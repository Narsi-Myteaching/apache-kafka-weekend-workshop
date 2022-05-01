package com.movie.ticket.service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.movie.ticket.bean.MovieTicket;



@Service
public class MovieTicketGeneratorServiceImpl implements MovieTicketGeneratorService{
	
	@Autowired
	private ObjectMapper mapper;
	
	
	
	private final String  movie_trailer_url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";

	@Override
	public byte[] generateMovieTicket(MovieTicket movieTicket) throws Exception {
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		
		movieTicket.setTicketNumber(UUID.randomUUID().toString());
		
		movieTicket.setTrailerUrl(movie_trailer_url);
		
		String movieTicketData = mapper.writeValueAsString(movieTicket);
		
		BitMatrix bitMatrix = qrCodeWriter.encode(movieTicketData, BarcodeFormat.QR_CODE, 150, 150);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFC041);

		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
		byte[] pngData = pngOutputStream.toByteArray();
		
		return pngData;
	}

}
