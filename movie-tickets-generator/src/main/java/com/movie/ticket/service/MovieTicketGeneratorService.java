package com.movie.ticket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.WriterException;
import com.movie.ticket.bean.MovieTicket;

public interface MovieTicketGeneratorService {

	public byte[] generateMovieTicket(MovieTicket movieTicket) throws Exception;
}
