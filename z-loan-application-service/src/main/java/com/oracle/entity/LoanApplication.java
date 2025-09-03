package com.oracle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private Long custId;
    
    private Long assignedAgentId;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.SUBMITTED;
    
    private String rejectionReason; 
    private Double loanAmount;      // requested loan amount
    private Integer tenureMonths;

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
}
