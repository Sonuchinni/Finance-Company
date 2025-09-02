package com.oracle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.dto.CustomerDTO;
import com.oracle.dto.LoanApplicationDTO;
import com.oracle.entity.CreditScore;
import com.oracle.proxy.CustomerProxy;
import com.oracle.proxy.LoanApplicationProxy;
import com.oracle.repository.CreditScoreRepository;


@Service
public class LoanApprovalService {

	@Autowired
    private LoanApplicationProxy proxy;
	
	@Autowired
    private CustomerProxy customerProxy;  // talks to Customer microservice

    @Autowired
    private CreditScoreRepository creditRepo;

    // return pending applications for agent (calls LoanApplicationService)
    public List<LoanApplicationDTO> getPendingApplications(Long agentId) {
        return proxy.getPendingApplications(agentId);
    }

    // mark application IN_REVIEW + credit score
    public LoanApplicationDTO startReview(Long loanId) {
//        return proxy.updateStatus(appId, "IN_REVIEW", null);
    	// Step 1: Update status to IN_REVIEW in LoanApplication service
        LoanApplicationDTO loanApp = proxy.updateStatus(loanId, "IN_REVIEW", null);

        // Step 2: Get customer info from Customer service
        CustomerDTO customer = customerProxy.getCustomerById(loanApp.getCustId());

        // Step 3: Get credit score from local DB
        CreditScore score = creditRepo.findById(loanApp.getCustId()).orElse(null);

        // Step 4: Enrich DTO
        if (customer != null) {
            loanApp.setCustomerName(customer.getFname());
            loanApp.setCustomerEmail(customer.getEmail());
        }
        if (score != null) {
            loanApp.setCreditScore(score.getCreditScore());
        }

        return loanApp;
    }

    // approve
    public LoanApplicationDTO approve(Long loanId) {
        return proxy.updateStatus(loanId, "APPROVED", null);
    }

    // reject with missing/incorrect-docs message
    public LoanApplicationDTO reject(Long loanId, String missingDocs) {
        return proxy.updateStatus(loanId, "REJECTED", missingDocs);
    }

    // optionally fetch single application
    public LoanApplicationDTO getById(Long loanId) {
        return proxy.getApplicationById(loanId);
    }
}
