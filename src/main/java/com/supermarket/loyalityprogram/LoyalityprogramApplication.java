package com.supermarket.loyalityprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class LoyalityprogramApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoyalityprogramApplication.class, args);
	}

}
