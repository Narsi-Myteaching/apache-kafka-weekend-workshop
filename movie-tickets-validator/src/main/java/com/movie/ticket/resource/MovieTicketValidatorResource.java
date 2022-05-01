package com.movie.ticket.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.ticket.TicketStatusEnum;
import com.movie.ticket.event.MovieTicketEvent;
import com.movie.ticket.producer.MovieTicketsProducer;

@RestController
@RequestMapping("/api/v1")
public class MovieTicketValidatorResource {

	@Autowired
	private MovieTicketsProducer producer;
	
	@PostMapping("/validate-movie-tickets")
	public ResponseEntity<Object> validateTicket(@RequestBody MovieTicketEvent ticketEvent){
		
		try {
			boolean isValidTicket = producer.isValidateTicket(ticketEvent);
			Map<Integer, String> dataMap = new HashMap<Integer, String>();
			if(isValidTicket) {
				dataMap.put(HttpStatus.OK.value(), String.valueOf(isValidTicket));
				ticketEvent.setTicketStatus(TicketStatusEnum.VALID.name());
				producer.sendTicketEvents(ticketEvent);
				return ResponseEntity.status(HttpStatus.OK).body(dataMap);
			}else {
				dataMap.put(HttpStatus.OK.value(), String.valueOf(isValidTicket));
				return ResponseEntity.status(HttpStatus.OK).body(dataMap);
			}
			
		}catch (Exception e) {
			Map<Integer, String> dataMap = new HashMap<Integer, String>();
			String errorMsg = "Server is Too Busy!! please show your ticket again";
			dataMap.put(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dataMap);
		}		
	}
}
