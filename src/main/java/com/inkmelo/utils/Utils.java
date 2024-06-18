package com.inkmelo.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
	
	public static Timestamp getCurrentTimestamp() {
		var date = new Date();
		var timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	public static ResponseEntity<?> generateMessageResponseEntity(String message, HttpStatus status) {
		var response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("timestamp", getCurrentTimestamp());
		response.put("status", status.value());
		return new ResponseEntity<>(response, status);
	}
}
