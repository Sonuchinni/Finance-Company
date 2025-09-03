package com.oracle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.AccountStatus;
import com.oracle.entity.Customer;
import com.oracle.entity.FDTransaction;
import com.oracle.entity.FixedDepositAccount;
import com.oracle.exception.AccountNotApprovedException;
import com.oracle.exception.CustomerNotFoundException;
import com.oracle.repository.CustomerRepository;
import com.oracle.repository.FDTransactionRepository;
import com.oracle.repository.FixedDepositAccountRepository;

@Service
public class FDService {
	
	
	@Autowired
    private FixedDepositAccountRepository fdRepo;

    @Autowired
    private FDTransactionRepository fdTxnRepo;

    @Autowired
    private CustomerRepository customerRepo;

    // Create FD for a customer
    public FixedDepositAccount createFD(Long custId, Double amount, int tenureMonths) {
        Customer customer = customerRepo.findById(custId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // ✅ Customer must be approved
        if (customer.getStatus() != AccountStatus.APPROVED) {
            throw new AccountNotApprovedException("Customer must be approved before opening FD.");
        }

        // ✅ Customer should not already have an FD
        if (customer.getFdAccount() != null) {
            throw new RuntimeException("Customer already has an FD account.");
        }

        // ✅ Minimum FD amount
        if (amount < 5000) {
            throw new RuntimeException("Minimum FD amount is ₹5000.");
        }

        FixedDepositAccount fd = new FixedDepositAccount();
        fd.setCustId(custId);
        fd.setAmount(amount);
        fd.setTenureMonths(tenureMonths);
        fd.setStatus(AccountStatus.PENDING); // initially pending

        return fdRepo.save(fd);
    }

    // Approve/Reject FD
    public FixedDepositAccount updateFDStatus(Long fdId, AccountStatus status) {
        FixedDepositAccount fd = fdRepo.findById(fdId)
                .orElseThrow(() -> new RuntimeException("FD not found with id " + fdId));

        fd.setStatus(status);
        FixedDepositAccount updated = fdRepo.save(fd);

        if (status == AccountStatus.APPROVED) {
            // ✅ Log initial deposit
            FDTransaction txn = new FDTransaction();
            txn.setType("DEPOSIT");
            txn.setAmount(updated.getAmount());
            txn.setFdId(updated.getFdId());
            fdTxnRepo.save(txn);

            System.out.println("FD approved. Initial deposit of ₹" + updated.getAmount() + " logged.");
        } else if (status == AccountStatus.REJECTED) {
            // ✅ Revert back money
            System.out.println("FD request rejected. Initial deposit of ₹" + updated.getAmount()
                    + " reverted to customer (custId=" + updated.getCustId() + ").");
        }

        return updated;
    }

    // Close FD
    public FDTransaction closeFD(Long fdId) {
        FixedDepositAccount fd = fdRepo.findById(fdId)
                .orElseThrow(() -> new RuntimeException("FD not found with id " + fdId));

        // ✅ FD must be approved before closure
        if (fd.getStatus() != AccountStatus.APPROVED) {
            throw new AccountNotApprovedException("FD must be approved before closure.");
        }

        FDTransaction txn = new FDTransaction();
        txn.setType("CLOSURE");
        txn.setAmount(fd.getAmount());
        txn.setFdId(fd.getFdId());

        fdRepo.delete(fd); // FD is closed
        return fdTxnRepo.save(txn);
    }

//    // Get all FDs by customer
//    public List<FixedDepositAccount> getFDsByCustomer(Long custId) {
//        return fdRepo.findByCustId(custId);
//    }
    
    public FixedDepositAccount getFDsByCustomer(Long custId) {
        return fdRepo.findByCustId(custId)
                .orElseThrow(() -> new RuntimeException("FD account not found for customer " + custId));
    }
    
 //  Extend FD
    public String extendFD(Long fdId, int extraMonths) {
        FixedDepositAccount fd = fdRepo.findById(fdId)
                .orElseThrow(() -> new RuntimeException("FD account not found"));

        if (fd.getStatus() != AccountStatus.APPROVED) {
            throw new RuntimeException("Only approved FD can be extended.");
        }

        fd.setTenureMonths(fd.getTenureMonths() + extraMonths);
        fdRepo.save(fd);

        FDTransaction txn = new FDTransaction();
        txn.setType("EXTENSION");
        txn.setAmount(0.0); // no new deposit
        txn.setFdId(fd.getFdId());
        fdTxnRepo.save(txn);

        return "FD tenure extended by " + extraMonths + " months.";
    }
    
    // Deduct amount from FD account
    public FixedDepositAccount deductFromFd(Long custId, Double amount) {
        FixedDepositAccount fd = fdRepo.findByCustId(custId)
                .orElseThrow(() -> new RuntimeException("FD account not found for customer " + custId));

        if (fd.getAmount() < amount) {
            throw new RuntimeException("Insufficient FD balance. Required: " + amount + ", Available: " + fd.getAmount());
        }

        fd.setAmount(fd.getAmount() - amount);
        return fdRepo.save(fd);
    }
}
