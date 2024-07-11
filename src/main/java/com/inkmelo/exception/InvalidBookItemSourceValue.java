package com.inkmelo.exception;

public class InvalidBookItemSourceValue extends RuntimeException {
	public InvalidBookItemSourceValue() {
		super();
	}
	
	public InvalidBookItemSourceValue(String message) {
		super(message);
	}
}
