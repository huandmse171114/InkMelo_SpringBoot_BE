package com.inkmelo.exception;

public class NoGenreFoundException extends RuntimeException {
	
	public NoGenreFoundException() {
		super();
	}
	
	public NoGenreFoundException(String message) {
		super(message);
	}
	
	public NoGenreFoundException(Integer id) {
		super("Genre with id " + id + " is not found.");
	}
	
}
