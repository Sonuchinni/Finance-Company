package com.oracle.entity;


import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter 
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "zc")
public class Customer {
	
		public enum Gender {
	        MALE, FEMALE, OTHER
	    }

	 	@Id @GeneratedValue
	 	@Column(name = "cust_id")
	    private long custId;
	 	
	 	//personal 
	 	@Column(length=10)
	 	private String fname;
	 	
	 	@Column(length=10)
	    private String lname;
	    private LocalDate dob;
	    @Enumerated(EnumType.STRING)
	    private Gender gender;
	    
	    //Identity proof
	    private int aadhar;
	    @Column(length=10)
	    private String pan;
	    @Column(length=10)
	    private String voterId;
	    @Column(length=10)
	    private String Passport;
	    private int ration;
	    @Column(length=10)
	    private String drivingLicence;
	    
	    //contact details
	    @Column(length=10)
	    private String email;
	    private int phoneNum;
	    
	    //nominee details
	    @Column(length=10)
	    private String nomineeName;
	    @Column(length=10)
	    private String nomineeAddress;
	    private int nomineeAge;
	    @Column(length=10)
	    private String relation;
	    
	    
	    //account status
	    @Enumerated(EnumType.STRING)
	    private AccountStatus Status = AccountStatus.PENDING;
	    
	    
	    @OneToOne
	    @JoinColumn(name = "savingsId", referencedColumnName = "savingsId")
	    private SavingsAccount savingsAccount;
	    
	    @OneToOne
	    @JoinColumn(name = "fdId", referencedColumnName = "fdId")
	    private FixedDepositAccount fdAccount;


}
