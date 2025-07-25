package com.techchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FiapTechChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapTechChallengeApplication.class, args);
	}

}
