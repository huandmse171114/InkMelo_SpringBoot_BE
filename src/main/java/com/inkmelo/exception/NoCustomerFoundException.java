package com.inkmelo.exception;

public class NoCustomerFoundException extends RuntimeException {
	
	public NoCustomerFoundException() {
		super();
	}
	
	public NoCustomerFoundException(String message) {
		super(message);
	}
}
