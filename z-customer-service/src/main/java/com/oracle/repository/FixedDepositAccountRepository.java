package com.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.FixedDepositAccount;

public interface FixedDepositAccountRepository extends JpaRepository<FixedDepositAccount, Long>{

	List<FixedDepositAccount> findByCustId(Long custId);
}
