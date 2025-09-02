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
    private String documents;  // simple string (comma-separated)
    private String status;     // SUBMITTED / IN_REVIEW / APPROVED / REJECTED
    private Long assignedAgentId;
    
    
 // Common docs (simple strings, not uploads)
    private String propertyDocs;    //sale agreement, builder NOC, allotment letter, title deed, etc.
    private String bankStatements;  //6–12 months
    private String incomeTaxReturns;//last 2–3 years, if self-employed
    private String employmentProof; //offer letter, form 16 for salaried

    private String vehicleQuotation; //Quotation/proforma invoice from dealer (for new vehicle)
    private String rcBook;           //RC Book & Insurance papers (for used car loan)
    private String insurancePapers;
    private String salarySlips;      //Salary slips / ITR (to assess repayment ability)

    private String goldDetails;  // ornaments info

    private String existingLoanStatements; //if any
    
    private Integer creditScore;
    private String customerName;
    private String customerEmail;
}
