package com.movie.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class MovieTicketsGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketsGeneratorApplication.class, args);
	}
	
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
