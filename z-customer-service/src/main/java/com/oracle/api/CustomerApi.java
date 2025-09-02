package com.oracle.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.CustomerDTO;
import com.oracle.entity.AccountStatus;
import com.oracle.entity.Customer;
import com.oracle.repository.CustomerRepository;
import com.oracle.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerApi {
	
    @Autowired
    private CustomerService customerService;
    
    // Get all customers
    @GetMapping
    public List<Customer> getAllZcustomers() {
        return customerService.getAllZcustomers();
    }
    
    // Get customer by ID
    @GetMapping("/by-id")
    public Optional<Customer> getZcustomerById(@RequestHeader("id") Long id) {
        return customerService.getZcustomerById(id);
    }
    
    // Create customer
    @PostMapping
    public Customer saveZcustomer(@RequestBody Customer customer) {
        return customerService.saveZcustomer(customer);
    }
    
    // Delete customer
    @DeleteMapping("/delete")
    public void deleteCustomer(@RequestHeader("id") Long id) {
        customerService.deleteZcustomerById(id);
    }
    
    // Update account status (approve/reject)
    @PutMapping("/status")
    public Optional<Customer> updateStatus(@RequestHeader("id") Long id,
                                            @RequestHeader("status") AccountStatus status) {
        return customerService.updateAccountStatus(id, status);
    }
    
    @GetMapping("/{custId}")
    public CustomerDTO getCustomerById(@PathVariable Long custId) {
        return customerService.getCustomerById(custId);
    }
}
