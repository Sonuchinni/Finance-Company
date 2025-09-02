package com.oracle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter 
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountDTO {

	private Long savingsId;
    private Long custId;
    private Double balance;
    private String status;
}
