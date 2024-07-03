package com.inkmelo.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
	
	public static Timestamp getCurrentTimestamp() {
		var date = new Date();
		var timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	public static ResponseEntity<?> generateMessageResponseEntity(String message, HttpStatus status) {
		return new ResponseEntity<>(MessageResponseDTO.builder()
				.message(message)
				.timestamp(getCurrentTimestamp())
				.status(status.value())
				.build(), status);
	}
	
	public static ResponseEntity<?> generatePagingListResponseEntity(long totalItems, List items,
			int totalPages, int currentPage, HttpStatus status) {
		var response = PagingListResposneDTO.builder()
				.totalItems(totalItems)
				.currentPage(currentPage)
				.items(items)
				.totalPages(totalPages)
				.build();
		
		return new ResponseEntity<>(response, status);
	}

	public static Timestamp stringToTimestamp(String date) {
		
		date = date.substring(0, 4) + "-" + date.substring(4);
	    date = date.substring(0, 7) + "-" + date.substring(7);
	    date = date.substring(0, 10) + " " + date.substring(10);
	    date = date.substring(0, 13) + ":" + date.substring(13);
	    date = date.substring(0, 16) + ":" + date.substring(16);
		
		return Timestamp.valueOf(date);
	}
}
