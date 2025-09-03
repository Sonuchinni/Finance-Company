package com.oracle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.FixedDepositAccount;

public interface FixedDepositAccountRepository extends JpaRepository<FixedDepositAccount, Long>{

	Optional<FixedDepositAccount> findByCustId(Long custId);
}
