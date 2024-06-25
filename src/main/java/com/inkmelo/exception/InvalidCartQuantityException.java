package com.inkmelo.exception;

public class InvalidCartQuantityException extends RuntimeException {
	public InvalidCartQuantityException() {
		super();
	}
	
	public InvalidCartQuantityException(String message) {
		super(message);
	}
}
