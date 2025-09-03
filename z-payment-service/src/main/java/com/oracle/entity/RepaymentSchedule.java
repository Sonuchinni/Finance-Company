package com.oracle.entity;

import java.time.LocalDate;

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
public class RepaymentSchedule {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Long loanId;
    private int installmentNo;
    private LocalDate dueDate;
    private Double emiAmount;

    @Enumerated(EnumType.STRING)
    private RepaymentStatus status = RepaymentStatus.PENDING;
}
