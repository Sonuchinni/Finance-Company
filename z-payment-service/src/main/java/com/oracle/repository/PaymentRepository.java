package com.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oracle.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	List<Payment> findByLoanId(Long loanId);
    @Query("SELECT COALESCE(SUM(p.amountPaid), 0) FROM Payment p WHERE p.loanId = :loanId")
    Double findTotalPaid(@Param("loanId") Long loanId);
}
