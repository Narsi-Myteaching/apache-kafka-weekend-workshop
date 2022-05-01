package com.movie.ticket.resource;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.ticket.bean.MovieTicket;
import com.movie.ticket.service.MovieTicketGeneratorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MovieTicketGenerator {
	
	@Autowired
	private MovieTicketGeneratorService service;

	@PostMapping("/movie-tickets")
	public void createMovieTicket(@RequestBody MovieTicket movieTicket, HttpServletResponse response) {
		Map<Integer, String> respMap = new HashMap<Integer, String>();
		 response.setContentType("image/png");
		try {
			byte[] ticket = service.generateMovieTicket(movieTicket);
			 OutputStream outputStream = response.getOutputStream();
			 outputStream.write(ticket);
			//return ResponseEntity.ok(ticket);
		}catch (Exception e) {
			respMap.put(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server is Too Busy!! Try Again");
			//return ResponseEntity.internalServerError().body(respMap);
		}
	}
}
