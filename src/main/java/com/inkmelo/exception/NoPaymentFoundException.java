package com.inkmelo.exception;

public class NoPaymentFoundException extends RuntimeException {
	public NoPaymentFoundException() {
		super();
	}
	
	public NoPaymentFoundException(String message) {
		super(message);
	}
}
