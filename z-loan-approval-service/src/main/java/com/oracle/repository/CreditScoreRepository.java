package com.oracle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.CreditScore;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long>{

}
