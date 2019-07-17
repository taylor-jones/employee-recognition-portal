package com.ttt.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {"com.ttt.erp.repository"})
@SpringBootApplication
public class ErpApplication {
	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	}
}
