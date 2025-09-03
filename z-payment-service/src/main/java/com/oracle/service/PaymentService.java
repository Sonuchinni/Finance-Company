package com.oracle.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.dto.FixedDepositAccountDTO;
import com.oracle.dto.LoanApplicationDTO;
import com.oracle.entity.Payment;
import com.oracle.entity.PaymentMethod;
import com.oracle.entity.RepaymentSchedule;
import com.oracle.entity.RepaymentStatus;
import com.oracle.proxy.FixedDepositProxy;
import com.oracle.proxy.LoanApplicationProxy;
import com.oracle.repository.PaymentRepository;
import com.oracle.repository.RepaymentScheduleRepository;

@Service
public class PaymentService {

	@Autowired
    private LoanApplicationProxy loanProxy;

	@Autowired
    private FixedDepositProxy fdProxy;
	
    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private RepaymentScheduleRepository scheduleRepo;

    // Loan Interest Rates
    private double getInterestRate(String loanType) {
        switch (loanType.toUpperCase()) {
            case "HOME": return 0.08;
            case "AUTO": return 0.09;
            case "GOLD": return 0.10;
            case "LAP":  return 0.11;
            default: throw new RuntimeException("Unknown loan type: " + loanType);
        }
    }

    // EMI calculation (Reducing Balance method)
    private double calculateEMI(double principal, double annualRate, int tenureMonths) {
        double monthlyRate = annualRate / 12.0;
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths))
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    // Loan disbursement → generates repayment schedule
    public List<RepaymentSchedule> disburseLoan(Long loanId) {
        LoanApplicationDTO loan = loanProxy.getApplicationById(loanId);

        if (!"APPROVED".equalsIgnoreCase(loan.getStatus())) {
            throw new RuntimeException("Loan not approved, cannot disburse.");
        }

        double principal = loan.getLoanAmount();
        int tenure = loan.getTenureMonths();
        double rate = getInterestRate(loan.getLoanType());

        double emi = calculateEMI(principal, rate, tenure);

        List<RepaymentSchedule> schedules = new ArrayList<>();
        for (int i = 1; i <= tenure; i++) {
            RepaymentSchedule rs = new RepaymentSchedule();
            rs.setLoanId(loanId);
            rs.setInstallmentNo(i);
            rs.setDueDate(LocalDate.now().plusMonths(i));
            rs.setEmiAmount(emi);
            rs.setStatus(RepaymentStatus.PENDING);
            schedules.add(rs);
        }

        return scheduleRepo.saveAll(schedules);
    }

    // Get repayment schedule
    public List<RepaymentSchedule> getSchedule(Long loanId) {
        List<RepaymentSchedule> schedules = scheduleRepo.findByLoanId(loanId);

        // Check overdue & apply penalty
        for (RepaymentSchedule rs : schedules) {
            if (rs.getStatus() == RepaymentStatus.PENDING && rs.getDueDate().isBefore(LocalDate.now())) {
                long monthsOverdue = ChronoUnit.MONTHS.between(rs.getDueDate(), LocalDate.now());
                if (monthsOverdue > 0) {
                    double penalty = rs.getEmiAmount() * 0.02 * monthsOverdue;
                    rs.setEmiAmount(rs.getEmiAmount() + penalty);
                    rs.setStatus(RepaymentStatus.OVERDUE);
                }
            }
        }
        return scheduleRepo.saveAll(schedules);
    }

    // Get payment history
    public List<Payment> getHistory(Long loanId) {
        return paymentRepo.findByLoanId(loanId);
    }

 // Make a payment
    public Payment makePayment(Long loanId, Double amount, PaymentMethod method) {
        List<RepaymentSchedule> schedules = scheduleRepo.findByLoanId(loanId);
        RepaymentSchedule nextDue = schedules.stream()
                .filter(s -> s.getStatus() == RepaymentStatus.PENDING || s.getStatus() == RepaymentStatus.OVERDUE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No pending installments."));
        double emi = nextDue.getEmiAmount();
        if (amount < emi) {
            throw new RuntimeException("Payment less than EMI amount.");
        }
        // Mark this installment paid
        nextDue.setStatus(RepaymentStatus.PAID);
        scheduleRepo.save(nextDue);
        // If customer pays extra → reduce principal and recalc EMIs
        double extra = amount - emi;
        if (extra > 0) {
            LoanApplicationDTO loan = loanProxy.getApplicationById(loanId);
            // Fetch the entire schedule
            List<RepaymentSchedule> remainingSchedule = schedules.stream()
                    .filter(s -> s.getStatus() != RepaymentStatus.PAID)
                    .toList();
            // Recalculate outstanding principal.
            double principal = loan.getLoanAmount();
            double totalPaid = paymentRepo.findTotalPaid(loanId);
            double outstandingPrincipal = principal - totalPaid - extra;
            int remainingMonths = remainingSchedule.size();
            if (remainingMonths > 0) {
                double newEmi = calculateEMI(outstandingPrincipal, getInterestRate(loan.getLoanType()), remainingMonths);
                remainingSchedule.forEach(s -> s.setEmiAmount(newEmi));
                scheduleRepo.saveAll(remainingSchedule);
            }
        }
        // Record payment
        Payment payment = new Payment();
        payment.setLoanId(loanId);
        payment.setAmountPaid(amount);
        payment.setPaymentDate(LocalDate.now());
        payment.setMethod(method);
        payment.setStatus("SUCCESS");
        return paymentRepo.save(payment);
    }
    
 // Check loan closure
    public String closeLoan(Long loanId) {
        LoanApplicationDTO loan = loanProxy.getApplicationById(loanId);

        double outstanding = calculateOutstanding(loanId);

        if (outstanding <= 0) {
            return "Loan closed successfully!";
        }

        // Step 1: Check FD
        FixedDepositAccountDTO fd = fdProxy.getFdAccount(loan.getCustId());

        if (fd != null && fd.getAmount() >= outstanding) {
            fdProxy.deductFromFd(loan.getCustId(), outstanding);
            return "Outstanding recovered from FD. Loan closed.";
        }

        // Step 2: Seize property
        return "Loan defaulted! Property seized: ";
    }

    private double calculateOutstanding(Long loanId) {
        Optional<Double> totalScheduledPayment = scheduleRepo.findByLoanId(loanId).stream()
            .map(RepaymentSchedule::getEmiAmount)
            .reduce(Double::sum);
        Double totalPaid = paymentRepo.findTotalPaid(loanId);
        return totalScheduledPayment.orElse(0.0) - totalPaid;
    }
}
