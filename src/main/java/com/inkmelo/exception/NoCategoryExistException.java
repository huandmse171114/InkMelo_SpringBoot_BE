package com.inkmelo.exception;

public class NoCategoryExistException extends RuntimeException {

		public NoCategoryExistException() {
			super();
		}
		
		public NoCategoryExistException(String message) {
			super(message);
		}
}
