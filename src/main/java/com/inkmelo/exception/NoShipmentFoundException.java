package com.inkmelo.exception;

public class NoShipmentFoundException extends RuntimeException{
	
	public NoShipmentFoundException() {
		super();
	}
	
	public NoShipmentFoundException(String message) {
		super(message);
	}
}
