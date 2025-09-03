package com.oracle.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entity.FDTransaction;
import com.oracle.entity.FixedDepositAccount;
import com.oracle.repository.FDTransactionRepository;
import com.oracle.repository.FixedDepositAccountRepository;

@Service
public class FDTransactionService {

	@Autowired
    private FDTransactionRepository txnRepo;
	
	@Autowired
    private FixedDepositAccountRepository fdRepo;

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
    
 // ✅ Deduct amount from FD & create transaction
    public boolean deductFromFd(Long fdId, Double amount) {
        FixedDepositAccount fd = fdRepo.findById(fdId)
                .orElseThrow(() -> new RuntimeException("FD account not found"));

        if (fd.getAmount() < amount) {
            return false; // insufficient balance
        }

        // Deduct amount
        fd.setAmount(fd.getAmount() - amount);
        fdRepo.save(fd);

        // Save transaction record
        FDTransaction txn = new FDTransaction();
        txn.setFdId(fdId);
        txn.setAmount(amount);
        txn.setType("DEBIT");
        txn.setTimestamp(LocalDateTime.now());
        txnRepo.save(txn);

        return true;
    }
}
