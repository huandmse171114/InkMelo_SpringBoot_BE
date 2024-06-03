package com.inkmelo.exception;

public class NoGenreExistException extends RuntimeException {
	
	public NoGenreExistException() {
		super();
	}
	
	public NoGenreExistException(String message) {
		super(message);
	}
}
