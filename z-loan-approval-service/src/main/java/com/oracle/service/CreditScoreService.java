package com.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.dto.CustomerDTO;
import com.oracle.entity.CreditScore;
import com.oracle.proxy.CustomerProxy;
import com.oracle.repository.CreditScoreRepository;

@Service
public class CreditScoreService {

	@Autowired
    private CreditScoreRepository repo;

    @Autowired
    private CustomerProxy customerProxy;

    // Fetch customer and prepare entry (if not already there)
    public CreditScore createCreditScore(Long custId) {
        CustomerDTO customer = customerProxy.getCustomerById(custId);

        if (customer == null) {
            throw new RuntimeException("Customer not found in CustomerService");
        }

        // Only prepare customer record if not present
        if (repo.existsById(custId)) {
            return repo.findById(custId).get();
        }

        CreditScore score = new CreditScore();
        score.setCustId(customer.getCustId());
        score.setName(customer.getFname());
        score.setEmail(customer.getEmail());
        score.setCreditScore(null); 

        return repo.save(score);
    }

    public CreditScore getScore(Long custId) {
        return repo.findById(custId)
                .orElseThrow(() -> new RuntimeException("Credit score not found"));
    }
}
