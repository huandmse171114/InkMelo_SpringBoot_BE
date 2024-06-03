package com.inkmelo.exception;

public class NoPublisherExistException extends RuntimeException{
	
	public NoPublisherExistException() {
		super();
	}
	
	public NoPublisherExistException(String message) {
		super(message);
	}
}
