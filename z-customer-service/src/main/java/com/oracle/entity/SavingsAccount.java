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
public class SavingsAccount {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

	@Column(name = "cust_id")
    private Long custId;
	
    private Double balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.PENDING;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "savings_id") 
    private List<SavingsTransaction> transactions;
    
//    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL)
//    private List<SavingsTransaction> transactions;
//    

//  @OneToOne
//  @JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID",unique = true)
//  private Customer customer;
}
