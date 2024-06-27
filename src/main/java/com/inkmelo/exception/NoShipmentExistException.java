package com.inkmelo.exception;

public class NoShipmentExistException extends RuntimeException{
	
	public NoShipmentExistException() {
		super();
	}
	
	public NoShipmentExistException(String message) {
		super(message);
	}
}
