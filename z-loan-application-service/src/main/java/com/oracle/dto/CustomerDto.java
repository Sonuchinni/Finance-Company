package com.oracle.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter 
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	private Long custId;
    private String name;
    private String dob;
    private String status;
    private int aadhar;
}
