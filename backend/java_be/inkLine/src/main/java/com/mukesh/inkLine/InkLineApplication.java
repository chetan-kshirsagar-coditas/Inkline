package com.mukesh.inkLine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InkLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(InkLineApplication.class, args);
	}

}
