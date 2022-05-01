package com.movie.ticket.listerner;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MovieTicketsTopicListener {

	@KafkaListener(topics = {"movie-tickets-topic"})
	public void onMessage(ConsumerRecord<String, String> consumerRecord) {
		System.out.println("consumer record is:\t");
		String value = consumerRecord.value();
		System.out.println("the record recived is:\t");
		System.out.println(value);
	}
	
}
