package com.inkmelo.exception;

public class NoBookFoundException extends RuntimeException {
	
	public NoBookFoundException() {
		super();
	}
	
	public NoBookFoundException(String message) {
		super(message);
	}
	
	public NoBookFoundException(Integer id) {
		super("Cuốn sách với mã số " + id + " không tìm thấy trong cơ sở dữ liệu.");
	}
}
