package com.oracle.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.entity.LoanAgent;
import com.oracle.repository.LoanAgentRepository;

@Configuration
public class DataLoader {

	@Bean
    CommandLineRunner initDatabase(LoanAgentRepository agentRepo) {
        return args -> {
            if (agentRepo.count() == 0) {
                agentRepo.save(new LoanAgent(null, "Ravi Kumar", "HOME"));
                agentRepo.save(new LoanAgent(null, "Anita Sharma", "AUTO"));
                agentRepo.save(new LoanAgent(null, "Suresh Patel", "GOLD"));
                agentRepo.save(new LoanAgent(null, "Priya Nair", "LAP"));
            }
        };
    }
}
