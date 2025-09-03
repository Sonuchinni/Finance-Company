package com.oracle.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.entity.LoanApplication;
import com.oracle.entity.LoanStatus;
import com.oracle.service.LoanApplicationService;

@RestController
@RequestMapping("/loans")
public class LoanApplicationController {

	@Autowired
    private LoanApplicationService service;

    @PostMapping("/apply")
    public LoanApplication applyLoan(@RequestHeader("custId") Long custId,
                                     @RequestBody LoanApplication loan) {
        return service.applyLoan(custId, loan);
    }
    
 // Added a GET method to retrieve a loan application by ID
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable("loanId") Long loanId) {
        LoanApplication loanApplication = service.getApplication(loanId);
        if (loanApplication != null) {
            return ResponseEntity.ok(loanApplication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // Agent fetches pending applications
    @GetMapping("/agent/{agentId}/pending")
    public List<LoanApplication> getPending(@PathVariable Long agentId) {
        return service.getPendingApplications(agentId);
    }

    // Agent updates status (IN_REVIEW, APPROVED, REJECTED)
    @PutMapping("/{loanId}/status")
    public LoanApplication updateStatus(@PathVariable Long loanId,
                                        @RequestParam("status") String status,
                                        @RequestParam(value = "remarks", required = false) String remarks) {
        return service.updateStatus(loanId, LoanStatus.valueOf(status), remarks);
    }

    // Get a specific loan application
//    @GetMapping("/{loanId}")
//    public LoanApplication getById(@PathVariable Long loanId) {
//        return service.getApplication(loanId);
//    }
}
