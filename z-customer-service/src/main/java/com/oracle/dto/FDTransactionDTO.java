package com.oracle.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FDTransactionDTO {

	private Long txnId;
    private String type;
    private Double amount;
    private LocalDateTime timestamp;
    private Long fdId;
}
