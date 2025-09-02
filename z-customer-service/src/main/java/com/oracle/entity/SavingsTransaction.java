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
public class SavingsTransaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    private String type; // "DEPOSIT" or "WITHDRAWAL"
    private Double amount;
    private LocalDateTime timestamp = LocalDateTime.now();

//    @ManyToOne
//    @JoinColumn(name = "savings_id")
//    private SavingsAccount savingsAccount;
    
    @Column(name = "savings_id", insertable = false, updatable = false)
    private Long savingsId;
}
