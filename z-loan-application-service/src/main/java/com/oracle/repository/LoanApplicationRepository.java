package com.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.LoanApplication;
import com.oracle.entity.LoanStatus;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long>{

	// Find all loan applications for an agent with a particular status
    List<LoanApplication> findByAssignedAgentIdAndStatus(Long agentId, LoanStatus status);

    // (Optional) If you want all loans for an agent regardless of status
    List<LoanApplication> findByAssignedAgentId(Long agentId);
}
