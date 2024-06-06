package com.inkmelo.exception;

public class NoUserExistException extends RuntimeException {
	
	public NoUserExistException() {
		super();
	}
	
	public NoUserExistException(String message) {
		super(message);
	}
}
