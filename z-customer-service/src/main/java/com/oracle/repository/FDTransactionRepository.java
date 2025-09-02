package com.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.FDTransaction;

public interface FDTransactionRepository extends JpaRepository<FDTransaction, Long>{

	List<FDTransaction> findByFdId(Long fdId);
}
