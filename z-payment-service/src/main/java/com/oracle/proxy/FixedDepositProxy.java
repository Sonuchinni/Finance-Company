package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.dto.FixedDepositAccountDTO;

@FeignClient(name = "z-customer-service")
public interface FixedDepositProxy {

	@GetMapping("/fd/{custId}")
    FixedDepositAccountDTO getFdAccount(@PathVariable Long custId);

    @PutMapping("/fd/{custId}/deduct")
    FixedDepositAccountDTO deductFromFd(@PathVariable Long custId, @RequestParam Double amount);
}
