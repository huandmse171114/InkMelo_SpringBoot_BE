package com.inkmelo.exception;

public class NoBookPackageExistException extends RuntimeException {
	
	public NoBookPackageExistException() {
		super();
	}
	
	public NoBookPackageExistException(String message) {
		super(message);
	}
}
