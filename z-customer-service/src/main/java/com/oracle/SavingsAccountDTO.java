package com.oracle;

import com.oracle.entity.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountDTO {

	private Long savingsId;
    private Long custId;
    private AccountStatus status;
}
