package com.inkmelo.exception;

public class NoBookExistException extends RuntimeException {
	
	public NoBookExistException() {
		super();
	}
	
	public NoBookExistException(String message) {
		super(message);
	}
} 
