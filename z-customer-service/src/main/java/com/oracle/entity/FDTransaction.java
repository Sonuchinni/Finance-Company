package com.oracle.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FDTransaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    private String type; // "DEPOSIT" or "CLOSURE"
    private Double amount;
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "fd_id", insertable = false, updatable = false)
    private Long fdId;
    
//    @ManyToOne
//    @JoinColumn(name = "fd_id")
//    private FixedDepositAccount fdAccount;
}
