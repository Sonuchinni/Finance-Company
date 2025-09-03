package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.oracle.dto.LoanApplicationDTO;

@FeignClient(name = "z-loan-application-service")
public interface LoanApplicationProxy {

	@GetMapping("/loans/{loanId}")
    LoanApplicationDTO getApplicationById(@PathVariable("loanId") Long loanId);
}
