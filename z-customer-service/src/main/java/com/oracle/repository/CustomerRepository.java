package com.oracle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
