package com.oracle.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.dto.LoanApplicationDTO;
import com.oracle.service.LoanApprovalService;


@RestController
@RequestMapping("/approval")
public class LoanApprovalApi {

	 	@Autowired
	    private LoanApprovalService service;

	    // Agents fetch pending apps assigned to them
	    @GetMapping("/pending")
	    public List<LoanApplicationDTO> getPending(@RequestHeader("agentId") Long agentId) {
	        return service.getPendingApplications(agentId);
	    }

	    // Start review -> status becomes IN_REVIEW in LoanApplication service
	    @PutMapping("/review/{loanId}")
	    public LoanApplicationDTO startReview(@PathVariable Long loanId) {
	        return service.startReview(loanId);
	    }

	    // Approve application
	    @PutMapping("/approve/{loanId}")
	    public LoanApplicationDTO approve(@PathVariable Long loanId) {
	        return service.approve(loanId);
	    }

	    // Reject application - provide missing docs / comments (agent sends)
	    @PutMapping("/reject/{loanId}")
	    public LoanApplicationDTO reject(@PathVariable Long loanId,
	                                     @RequestHeader("missingDocs") String missingDocs) {
	        return service.reject(loanId, missingDocs);
	    }

	    // Get application details
	    @GetMapping("/{loanId}")
	    public LoanApplicationDTO getById(@PathVariable Long loanId) {
	        return service.getById(loanId);
	    }
}
