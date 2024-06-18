package com.inkmelo.exception;

public class InvalidBookItemFieldValueException extends RuntimeException {
	
	public InvalidBookItemFieldValueException() {
		super();
	}
	
	public InvalidBookItemFieldValueException(String message) {
		super(message);
	}
}
