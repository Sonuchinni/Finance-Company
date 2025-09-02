package com.oracle.entity;

import java.util.List;

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
public class FixedDepositAccount {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fdId;

	@Column(name = "cust_id")
    private Long custId;
	
    private Double amount;
    private int tenureMonths;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.PENDING;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fd_id") 
    private List<FDTransaction> transactions;
    
//    @OneToOne
//    @JoinColumn(name = "cust_id", unique = true)
//    private Customer customer;
    
//    @OneToMany(mappedBy = "fdAccount", cascade = CascadeType.ALL)
//    private List<FDTransaction> transactions;
}
