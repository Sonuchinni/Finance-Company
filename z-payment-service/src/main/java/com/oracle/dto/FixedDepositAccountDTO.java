package com.oracle.dto;

import java.time.LocalDate;

import com.oracle.entity.RepaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FixedDepositAccountDTO {

	private Long fdId;
    private Long custId;
    private Double amount;
    private int tenureMonths;
    private String status;
}
