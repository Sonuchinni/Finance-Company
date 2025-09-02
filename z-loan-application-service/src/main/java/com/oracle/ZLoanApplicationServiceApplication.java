package com.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZLoanApplicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZLoanApplicationServiceApplication.class, args);
	}

}
