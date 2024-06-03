package com.inkmelo.exception;

public class NoPublisherFoundException extends RuntimeException {
	
	public NoPublisherFoundException() {
		super();
	}
	
	public NoPublisherFoundException(String message) {
		super(message);
	}
	
	public NoPublisherFoundException(Integer id) {
		super("Publisher with id " + id + " is not found.");
	}
	
}
