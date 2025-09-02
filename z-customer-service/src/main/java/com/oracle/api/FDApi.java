package com.oracle.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.entity.AccountStatus;
import com.oracle.entity.FDTransaction;
import com.oracle.entity.FixedDepositAccount;
import com.oracle.service.FDService;

@RestController
@RequestMapping("/fd")
public class FDApi {

//	@Autowired
//    private FDService fdService;
//
//    // Create FD
//    @PostMapping("/create")
//    public FixedDepositAccount createFD(@RequestHeader("custId") Long custId,
//                                        @RequestHeader("amount") Double amount,
//                                        @RequestHeader("tenureMonths") int tenureMonths) {
//        return fdService.createFD(custId, amount, tenureMonths);
//    }
//
//    // Close FD
//    @PostMapping("/close")
//    public FDTransaction closeFD(@RequestHeader("fdId") Long fdId) {
//        return fdService.closeFD(fdId);
//    }
//
//    // Approve/Reject FD
//    @PutMapping("/status")
//    public Optional<FixedDepositAccount> updateStatus(@RequestHeader("fdId") Long fdId,
//                                                      @RequestHeader("status") AccountStatus status) {
//        return fdService.updateFDStatus(fdId, status);
//    }
	
	
	@Autowired
    private FDService fdService;

    // Create FD
    @PostMapping("/create")
    public FixedDepositAccount createFD(
            @RequestHeader("custId") Long custId,
            @RequestHeader("amount") Double amount,
            @RequestHeader("tenureMonths") Integer tenureMonths) {
        return fdService.createFD(custId, amount, tenureMonths);
    }

    // Approve / Reject FD
    @PutMapping("/status")
    public FixedDepositAccount updateFDStatus(
            @RequestHeader("fdId") Long fdId,
            @RequestHeader("status") AccountStatus status) {
        return fdService.updateFDStatus(fdId, status);
    }

    // Close FD
    @PostMapping("/close")
    public FDTransaction closeFD(@RequestHeader("fdId") Long fdId) {
        return fdService.closeFD(fdId);
    }

    // Get all FDs by customer
    @GetMapping("/customer")
    public List<FixedDepositAccount> getFDsByCustomer(@RequestHeader("custId") Long custId) {
        return fdService.getFDsByCustomer(custId);
    }
    
    @PostMapping("/extend")
    public String extendFD(@RequestHeader Long fdId, @RequestHeader int extraMonths) {
        return fdService.extendFD(fdId, extraMonths);
    }
}
