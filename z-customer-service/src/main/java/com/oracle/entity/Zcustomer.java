package com.oracle.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter //automatically creates getters
@AllArgsConstructor
@NoArgsConstructor
public class Zcustomer {

	 @Id @GeneratedValue
	    private Long id;
	    private String name;
	    private String email;
	    private String password;
	    private String kycStatus;
	    private String incomeProof;
	    private String bankInfo;
	    private String role;
}
