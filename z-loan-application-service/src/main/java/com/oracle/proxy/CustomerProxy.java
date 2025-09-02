package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.oracle.dto.CustomerDto;
import com.oracle.dto.SavingsAccountDTO;

@FeignClient(name="z-customer-service")
public interface CustomerProxy {

//	@GetMapping("/by-id")
//    CustomerDto getCustomerById(@RequestHeader("id") Long id);
	
	@GetMapping("/savings/{custId}")
    SavingsAccountDTO getSavingsAccount(@PathVariable("custId") Long custId);
	
}
