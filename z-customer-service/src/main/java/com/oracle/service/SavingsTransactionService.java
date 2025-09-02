package com.oracle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.SavingsTransaction;
import com.oracle.repository.SavingsTransactionRepository;

@Service
public class SavingsTransactionService {

	@Autowired
    private SavingsTransactionRepository txnRepo;

    // Get all transactions
    public List<SavingsTransaction> getAllTransactions() {
        return txnRepo.findAll();
    }

    // Get transactions by savingsId
    public List<SavingsTransaction> getTransactionsBySavingsId(Long savingsId) {
        return txnRepo.findBySavingsId(savingsId);
    }

    // Get transaction by txnId
    public Optional<SavingsTransaction> getTransactionById(Long txnId) {
        return txnRepo.findById(txnId);
    }

    // Delete a transaction (optional)
    public void deleteTransaction(Long txnId) {
        txnRepo.deleteById(txnId);
    }
}
