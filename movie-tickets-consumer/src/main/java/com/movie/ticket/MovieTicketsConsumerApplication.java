package com.movie.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MovieTicketsConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketsConsumerApplication.class, args);
	}

}
