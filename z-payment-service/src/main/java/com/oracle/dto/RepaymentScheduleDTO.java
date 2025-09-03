package com.oracle.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentScheduleDTO {

	private int installmentNo;
    private LocalDate dueDate;
    private Double emiAmount;
    private String status;
}
