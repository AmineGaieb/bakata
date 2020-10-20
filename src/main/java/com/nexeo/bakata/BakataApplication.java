package com.nexeo.bakata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BakataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakataApplication.class, args);
	}
}
