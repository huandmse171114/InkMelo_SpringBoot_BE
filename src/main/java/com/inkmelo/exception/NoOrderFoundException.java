package com.inkmelo.exception;

public class NoOrderFoundException extends RuntimeException{
	public NoOrderFoundException() {
		super();
	}
	
	public NoOrderFoundException(String message) {
		super(message);
	}
}
