package com.oracle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.LoanAgent;

public interface LoanAgentRepository extends JpaRepository<LoanAgent, Long>{

	Optional<LoanAgent> findFirstBySpecialization(String specialization);
}
