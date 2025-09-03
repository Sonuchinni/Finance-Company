package com.oracle.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.dto.SavingsAccountDTO;
import com.oracle.entity.AccountStatus;
import com.oracle.entity.SavingsAccount;
import com.oracle.entity.SavingsTransaction;
import com.oracle.repository.SavingsAccountRepository;
import com.oracle.service.SavingsService;

@RestController
@RequestMapping("/savings")
public class SavingsApi {

	 	@Autowired
	    private SavingsService savingsService;
	 	
	 	@Autowired
	    private SavingsAccountRepository savingsRepo;

	 // Create Savings Account
	    @PostMapping("/create")
	    public SavingsAccount createSavings(@RequestHeader("custId") Long custId,
	                                        @RequestHeader("deposit") Double deposit) {
	        return savingsService.createSavings(custId, deposit);
	    }

	 // Deposit money
	    @PostMapping("/deposit")
	    public SavingsTransaction deposit(@RequestHeader("savingsId") Long savingsId,
	                                      @RequestHeader("amount") Double amount) {
	        return savingsService.deposit(savingsId, amount);
	    }


	 // Withdraw money
	    @PostMapping("/withdraw")
	    public SavingsTransaction withdraw(@RequestHeader("savingsId") Long savingsId,
	                                       @RequestHeader("amount") Double amount) {
	        return savingsService.withdraw(savingsId, amount);
	    }

	 // Approve/Reject savings account
	    @PutMapping("/status")
	    public Optional<SavingsAccount> updateStatus(@RequestHeader("savingsId") Long savingsId,
	                                                 @RequestHeader("status") AccountStatus status) {
	        return savingsService.updateSavingsStatus(savingsId, status);
	    }
	    
	    @GetMapping("/{custId}")
	    public SavingsAccountDTO getSavingsByCustId(@PathVariable Long custId) {
	        SavingsAccount account = savingsRepo.findByCustId(custId)
	            .orElseThrow(() -> new RuntimeException("Savings account not found for customer " + custId));

	        return new SavingsAccountDTO(account.getSavingsId(), account.getCustId(), account.getStatus());
	    }
}
