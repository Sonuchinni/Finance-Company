package com.oracle.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.entity.SavingsTransaction;
import com.oracle.service.SavingsTransactionService;

@RestController
@RequestMapping("/savings/transactions")
public class SavingsTransactionApi {

	private final SavingsTransactionService txnService;

    @Autowired
    public SavingsTransactionApi(SavingsTransactionService txnService) {
        this.txnService = txnService;
    }

    // Get all transactions
    @GetMapping
    public List<SavingsTransaction> getAllTransactions() {
        return txnService.getAllTransactions();
    }

    // Get transactions for a specific savings account
    @GetMapping("/by-savings")
    public List<SavingsTransaction> getTransactionsBySavingsId(@RequestHeader("savingsId") Long savingsId) {
        return txnService.getTransactionsBySavingsId(savingsId);
    }

    // Get single transaction by txnId
    @GetMapping("/by-id")
    public Optional<SavingsTransaction> getTransactionById(@RequestHeader("txnId") Long txnId) {
        return txnService.getTransactionById(txnId);
    }

    // Delete a transaction (admin/testing)
    @DeleteMapping("/delete")
    public void deleteTransaction(@RequestHeader("txnId") Long txnId) {
        txnService.deleteTransaction(txnId);
    }
}
