package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.oracle.dto.CustomerDTO;

@FeignClient(name = "z-customer-service")
public interface CustomerProxy {

	@GetMapping("/customers/{custId}")
    CustomerDTO getCustomerById(@PathVariable("custId") Long custId);
}
