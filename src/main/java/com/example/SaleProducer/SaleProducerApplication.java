package com.example.SaleProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleProducerApplication.class, args);
	}

	@Bean
	public ObjectMapper scmsObjectMapper() {
		com.fasterxml.jackson.databind.ObjectMapper responseMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		return responseMapper;
	}
}
