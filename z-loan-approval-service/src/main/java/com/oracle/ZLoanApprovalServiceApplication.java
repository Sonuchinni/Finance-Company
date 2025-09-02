package com.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZLoanApprovalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZLoanApprovalServiceApplication.class, args);
	}

}
