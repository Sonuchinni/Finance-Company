package com.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.RepaymentSchedule;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long>{

	List<RepaymentSchedule> findByLoanId(Long loanId);
}
