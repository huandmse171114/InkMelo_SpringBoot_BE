package com.inkmelo.exception;

public class NoCategoryFoundException extends RuntimeException {

		public NoCategoryFoundException() {
			super();
		}
		
		public NoCategoryFoundException(String message) {
			super(message);
		}
		
		public NoCategoryFoundException(Integer id) {
			super("Category with id " + id + " is not found.");
		}
}
