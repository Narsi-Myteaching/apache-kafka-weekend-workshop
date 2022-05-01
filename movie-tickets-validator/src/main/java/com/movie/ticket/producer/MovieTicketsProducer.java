package com.movie.ticket.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.event.MovieTicketEvent;

@Service
public class MovieTicketsProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${movie.tickets.topic}")
	private String topicName;
	
	@Value("${movie.tickets.partitions}")
	private int partitions;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public Boolean isValidateTicket(MovieTicketEvent ticketEvent) {
		//invoke the QR-CODE generator API to validate the ticket
		
		return true;
	}
	
	/**
	 * only valid/verified movie tickets will send to topic 
	 * @param ticketEvent
	 * @throws JsonProcessingException 
	 */
	public void sendTicketEvents(MovieTicketEvent ticketEvent) throws JsonProcessingException {
		
		String key = ticketEvent.getTicketNumber();
		String data = objectMapper.writeValueAsString(ticketEvent);
		System.out.println("topic name is:\t"+topicName);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName,key, data);
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				try {
					String recordData = objectMapper.writeValueAsString(result.getProducerRecord());
					System.out.println("event sent sucessfully and the event data is:\t");
					System.out.println(recordData);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			
			@Override
			public void onFailure(Throwable ex) {
				System.out.println("event has not been sent. the reason is:\t"+ex.getMessage());
				throw new RuntimeException(ex.getMessage());
			}
		});
		
	}
}

