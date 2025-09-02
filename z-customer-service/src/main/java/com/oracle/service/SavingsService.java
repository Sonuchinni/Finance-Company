package com.oracle.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.AccountStatus;
import com.oracle.entity.Customer;
import com.oracle.entity.SavingsAccount;
import com.oracle.entity.SavingsTransaction;
import com.oracle.exception.AccountNotApprovedException;
import com.oracle.exception.CustomerNotFoundException;
import com.oracle.exception.InsufficientBalanceException;
import com.oracle.repository.CustomerRepository;
import com.oracle.repository.SavingsAccountRepository;
import com.oracle.repository.SavingsTransactionRepository;

@Service
public class SavingsService {

	@Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private SavingsAccountRepository savingsRepo;

    @Autowired
    private SavingsTransactionRepository txnRepo;

    // Create savings account
    public SavingsAccount createSavings(Long custId, Double deposit) {
        Customer customer = customerRepo.findById(custId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (customer.getStatus() != AccountStatus.APPROVED) {
            throw new AccountNotApprovedException("Customer must be approved before opening a savings account.");
        }
        if (customer.getSavingsAccount() != null) {
            throw new RuntimeException("Customer already has a savings account.");
        }
        if (deposit < 1000) {
            throw new RuntimeException("Minimum initial deposit is ₹1000.");
        }

        SavingsAccount acc = new SavingsAccount();
        acc.setBalance(deposit);
        acc.setStatus(AccountStatus.PENDING);
        acc.setCustId(custId);

        SavingsAccount savedAcc = savingsRepo.save(acc);

        //  Link customer -> savings account
        customer.setSavingsAccount(savedAcc);
        customerRepo.save(customer);

        return savedAcc; // deposit logged only after approval
    }
    

    // Deposit
    public SavingsTransaction deposit(Long savingsId, Double amount) {
        SavingsAccount acc = savingsRepo.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings account not found"));

        if (acc.getStatus() != AccountStatus.APPROVED) {
            throw new AccountNotApprovedException("Savings account must be approved before transactions.");
        }
        
        acc.setBalance(acc.getBalance() + amount);
        savingsRepo.save(acc);

        SavingsTransaction txn = new SavingsTransaction();
        txn.setType("DEPOSIT");
        txn.setAmount(amount);
        txn.setSavingsId(acc.getSavingsId());
        return txnRepo.save(txn);
    }
    

    // Withdraw
    public SavingsTransaction withdraw(Long savingsId, Double amount) {
    	SavingsAccount account = savingsRepo.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings account not found"));

        if (account.getStatus() != AccountStatus.APPROVED) {
            throw new AccountNotApprovedException("Savings account must be approved before transactions.");
        }

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        account.setBalance(account.getBalance() - amount);
        savingsRepo.save(account);

        SavingsTransaction txn = new SavingsTransaction();
        txn.setType("WITHDRAW");
        txn.setAmount(amount);
        txn.setSavingsId(account.getSavingsId());
        return txnRepo.save(txn);
    }
    

    // Update status
    public Optional<SavingsAccount> updateSavingsStatus(Long savingsId, AccountStatus status) {
    	SavingsAccount account = savingsRepo.findById(savingsId)
                .orElseThrow(() -> new RuntimeException("Savings account not found"));

        account.setStatus(status);
        SavingsAccount updated = savingsRepo.save(account);

        if (status == AccountStatus.APPROVED) {
            //  Log initial deposit as the first transaction
            SavingsTransaction initTxn = new SavingsTransaction();
            initTxn.setType("INITIAL_DEPOSIT");
            initTxn.setAmount(updated.getBalance());
            initTxn.setSavingsId(updated.getSavingsId());
            txnRepo.save(initTxn);

            System.out.println("Savings account approved. Initial deposit logged.");
        } 
        else if (status == AccountStatus.REJECTED) {
            //  Log "reverted" message
            System.out.println("Savings account rejected. Initial deposit of " 
                                + updated.getBalance() + " reverted to customer (custId=" 
                                + updated.getCustId() + ").");

            // Optionally reset the balance to 0
            updated.setBalance(0.0);
            savingsRepo.save(updated);
        }

        return Optional.of(updated);
    }
    
}
