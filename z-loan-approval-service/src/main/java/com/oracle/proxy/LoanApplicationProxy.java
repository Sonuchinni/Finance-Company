package com.oracle.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.dto.LoanApplicationDTO;

@FeignClient(name = "z-loan-application-service")
public interface LoanApplicationProxy {

	/**
     * LoanApplicationService must expose:
     *   GET  /loans/agent/{agentId}/pending
     * which returns List<LoanApplicationDTO>
     */
    @GetMapping("/loans/agent/{agentId}/pending")
    List<LoanApplicationDTO> getPendingApplications(@PathVariable("agentId") Long agentId);

    /**
     * LoanApplicationService must expose:
     *   PUT /loans/{appId}/status?status=IN_REVIEW&remarks=...
     * This endpoint updates status and optionally stores remarks.
     */
    @PutMapping("/loans/{loanId}/status")
    LoanApplicationDTO updateStatus(@PathVariable("loanId") Long loanId,
                                    @RequestParam("status") String status,
                                    @RequestParam(value = "remarks", required = false) String remarks);

    /**
     * GET single application
     *   GET /loans/{appId}
     */
    @GetMapping("/loans/{loanId}")
    LoanApplicationDTO getApplicationById(@PathVariable("loanId") Long loanId);
}
