package com.oracle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.FDTransaction;
import com.oracle.repository.FDTransactionRepository;

@Service
public class FDTransactionService {

	@Autowired
    private FDTransactionRepository txnRepo;

    // Get all FD transactions
    public List<FDTransaction> getAllTransactions() {
        return txnRepo.findAll();
    }

    // Get transactions by FD Id
    public List<FDTransaction> getTransactionsByFdId(Long fdId) {
        return txnRepo.findByFdId(fdId);
    }

    // Get single transaction by txnId
    public Optional<FDTransaction> getTransactionById(Long txnId) {
        return txnRepo.findById(txnId);
    }

    // Delete a transaction (admin/testing)
    public void deleteTransaction(Long txnId) {
        txnRepo.deleteById(txnId);
    }
}
