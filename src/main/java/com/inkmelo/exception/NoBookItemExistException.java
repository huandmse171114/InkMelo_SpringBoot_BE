package com.inkmelo.exception;

public class NoBookItemExistException extends RuntimeException{
	
	public NoBookItemExistException() {
		super();
	}
	
	public NoBookItemExistException(String message) {
		super(message);
	}
}
