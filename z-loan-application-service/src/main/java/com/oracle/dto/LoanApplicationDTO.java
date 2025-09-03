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
    private String loanType;
    private String status;
    private Long assignedAgentId;
    private String rejectionReason;
    private Double loanAmount;      // requested loan amount
    private Integer tenureMonths;

    // docs (keep it simple)
    private String propertyDocs;
    private String bankStatements;
    private String incomeTaxReturns;
    private String employmentProof;

    private String vehicleQuotation;
    private String rcBook;
    private String insurancePapers;
    private String salarySlips;

    private String goldDetails;

    private String existingLoanStatements;
}
