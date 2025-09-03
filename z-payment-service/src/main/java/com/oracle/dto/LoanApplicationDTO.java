package com.oracle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {

	private Long loanId;
    private Long custId;
    private Double loanAmount;
    private String loanType;
    private Integer tenureMonths;
    private String status;
}
