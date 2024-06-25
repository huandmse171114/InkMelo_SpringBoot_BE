package com.inkmelo.exception;

public class NoCartDetailFoundException extends RuntimeException {
	
	public NoCartDetailFoundException() {
		super();
	}
	
	public NoCartDetailFoundException(String message) {
		super(message);
	}
}
