package com.inkmelo.exception;

public class DuplicateBookPackageException extends RuntimeException{
	
	public DuplicateBookPackageException() {
		super();
	}
	
	public DuplicateBookPackageException(String message) {
		super(message);
	}
}
