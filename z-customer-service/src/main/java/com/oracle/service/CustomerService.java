package com.oracle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.dto.CustomerDTO;
import com.oracle.entity.AccountStatus;
import com.oracle.entity.Customer;
import com.oracle.repository.CustomerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Service
public class CustomerService {

	
	@Autowired
    private CustomerRepository customerRepository;
	
    // Create or update a zcustomer
    public Customer saveZcustomer(Customer zcustomer) {
    	return customerRepository.save(zcustomer);
    }
    
    // Get a list of all zcustomer
    public List<Customer> getAllZcustomers() {
        return customerRepository.findAll();
    }
    
    // Get single zcustomer by id
    public Optional<Customer> getZcustomerById(Long id) {
        return customerRepository.findById(id);
    }
    
    // Delete zcustomer by id
    public void deleteZcustomerById(Long id) {
    	customerRepository.deleteById(id);
    }
    
    //update account status
    public Optional<Customer> updateAccountStatus(Long id, AccountStatus status){
    	Customer c = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        c.setStatus(status);
        return Optional.of(customerRepository.save(c));
    }
    
    public CustomerDTO getCustomerById(Long custId) {
        Customer customer = customerRepository.findById(custId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return new CustomerDTO(customer.getCustId(), customer.getFname(), customer.getEmail());
    }
}
