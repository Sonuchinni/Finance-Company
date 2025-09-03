package com.oracle.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.entity.FDTransaction;
import com.oracle.service.FDTransactionService;

@RestController
@RequestMapping("/fd/transactions")
public class FDTransactionApi {

	@Autowired
    private FDTransactionService txnService;

    // Get all FD transactions
    @GetMapping
    public List<FDTransaction> getAllTransactions() {
        return txnService.getAllTransactions();
    }

    // Get transactions by FD Id
    @GetMapping("/by-fd")
    public List<FDTransaction> getTransactionsByFdId(@RequestHeader("fdId") Long fdId) {
        return txnService.getTransactionsByFdId(fdId);
    }

    // Get transaction by txnId
    @GetMapping("/by-id")
    public Optional<FDTransaction> getTransactionById(@RequestHeader("txnId") Long txnId) {
        return txnService.getTransactionById(txnId);
    }

    // Delete a transaction (optional, admin only)
    @DeleteMapping("/delete")
    public void deleteTransaction(@RequestHeader("txnId") Long txnId) {
        txnService.deleteTransaction(txnId);
    }
    
 // ✅ Deduct amount from FD (called by Payment Service)
    @PostMapping("/deduct")
    public String deductFromFd(@RequestParam Long fdId, @RequestParam Double amount) {
        boolean success = txnService.deductFromFd(fdId, amount);
        return success ? "Amount deducted successfully from FD"
                       : "Insufficient FD balance";
    }
}
