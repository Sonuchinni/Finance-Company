package com.oracle.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.dto.SavingsAccountDTO;
import com.oracle.entity.LoanAgent;
import com.oracle.entity.LoanApplication;
import com.oracle.entity.LoanStatus;
import com.oracle.entity.LoanType;
import com.oracle.exception.AgentNotFoundException;
import com.oracle.exception.CustomerNotApprovedException;
import com.oracle.exception.InvalidDocumentException;
import com.oracle.exception.LoanNotFoundException;
import com.oracle.proxy.CustomerProxy;
import com.oracle.repository.LoanAgentRepository;
import com.oracle.repository.LoanApplicationRepository;

@Service
public class LoanApplicationService {

	@Autowired
    private LoanApplicationRepository repo;
	
	@Autowired
    private LoanAgentRepository agentRepo;

    @Autowired
    private CustomerProxy proxy;

    public LoanApplication applyLoan(Long custId, LoanApplication loan) {
        // check savings account
        SavingsAccountDTO savings = proxy.getSavingsAccount(custId);
        if (savings == null || !"APPROVED".equalsIgnoreCase(savings.getStatus())) {
            throw new CustomerNotApprovedException("Customer must have an APPROVED savings account to apply for loan.");
        }

        loan.setCustId(custId);
        loan.setStatus(LoanStatus.SUBMITTED);

        // Validate docs based on loan type
        validateDocuments(loan);

        // Assign loan agent
        assignAgent(loan);
        
        return repo.save(loan);
    }
    
    private void assignAgent(LoanApplication loan) {
        String specialization = loan.getLoanType().name(); // e.g. HOME, AUTO, GOLD, LAP
        LoanAgent agent = agentRepo.findFirstBySpecialization(specialization)
                .orElseThrow(() -> new AgentNotFoundException("No agent available for specialization: " + specialization));
        loan.setAssignedAgentId(agent.getAgentId());
    }

    private void validateDocuments(LoanApplication loan) {
        LoanType type = loan.getLoanType();

        switch (type) {
            case HOME:
                if (loan.getPropertyDocs() == null || loan.getBankStatements() == null) {
                    throw new InvalidDocumentException("Home Loan requires property documents and bank statements.");
                }
                break;
            case AUTO:
                if (loan.getVehicleQuotation() == null || loan.getBankStatements() == null) {
                    throw new InvalidDocumentException("Auto Loan requires vehicle quotation and bank statements.");
                }
                break;
            case GOLD:
                if (loan.getGoldDetails() == null) {
                    throw new InvalidDocumentException("Gold Loan requires Aadhaar and gold details.");
                }
                break;
            case LAP:
                if (loan.getPropertyDocs() == null || loan.getIncomeTaxReturns() == null) {
                    throw new InvalidDocumentException("Loan Against Property requires property docs and ITRs.");
                }
                break;
        }
    }
    
    //  For LoanApproval microservice

    public List<LoanApplication> getPendingApplications(Long agentId) {
        return repo.findByAssignedAgentIdAndStatus(agentId, LoanStatus.SUBMITTED);
    }

    public LoanApplication updateStatus(Long appId, LoanStatus status, String remarks) {
        LoanApplication app = repo.findById(appId)
                .orElseThrow(() -> new LoanNotFoundException("Loan application not found"));

        app.setStatus(status);
        if (status == LoanStatus.REJECTED) {
            app.setRejectionReason(remarks);
        }
        return repo.save(app);
    }

    public LoanApplication getApplication(Long appId) {
        return repo.findById(appId)
                .orElseThrow(() -> new LoanNotFoundException("Loan application not found"));
    }
	
}
