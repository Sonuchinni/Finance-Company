package com.oracle.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.entity.Payment;
import com.oracle.entity.PaymentMethod;
import com.oracle.entity.RepaymentSchedule;
import com.oracle.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentApi {

	@Autowired
    private PaymentService service;

    // Loan disbursement
    @PostMapping("/disburse/{loanId}")
    public List<RepaymentSchedule> disburse(@PathVariable Long loanId) {
        return service.disburseLoan(loanId);
    }

    // Repayment schedule
    @GetMapping("/schedule/{loanId}")
    public List<RepaymentSchedule> getSchedule(@PathVariable Long loanId) {
        return service.getSchedule(loanId);
    }

    // Payment history
    @GetMapping("/history/{loanId}")
    public List<Payment> getHistory(@PathVariable Long loanId) {
        return service.getHistory(loanId);
    }

    // Make a repayment
    @PostMapping("/pay/{loanId}")
    public Payment pay(@PathVariable Long loanId,
                       @RequestParam Double amount,
                       @RequestParam PaymentMethod method) {
        return service.makePayment(loanId, amount, method);
    }
    
    @PutMapping("/close/{loanId}")
    public ResponseEntity<String> closeLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(service.closeLoan(loanId));
    }
	
	
}
