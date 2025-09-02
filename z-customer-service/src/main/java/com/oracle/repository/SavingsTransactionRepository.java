package com.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.SavingsTransaction;

public interface SavingsTransactionRepository extends JpaRepository<SavingsTransaction, Long>{

	List<SavingsTransaction> findBySavingsId(Long savingsId);
}
