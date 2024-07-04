package com.inkmelo.exception;

public class BookPackageOutOfStockException extends RuntimeException {
	public BookPackageOutOfStockException() {
		super();
	}
	
	public BookPackageOutOfStockException(String message) {
		super(message);
	}
}
