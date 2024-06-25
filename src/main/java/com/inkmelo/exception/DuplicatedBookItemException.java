package com.inkmelo.exception;

public class DuplicatedBookItemException extends RuntimeException {
	
	public DuplicatedBookItemException() {
		super();
	}
	
	public DuplicatedBookItemException(String message) {
		super(message);
	}
}
