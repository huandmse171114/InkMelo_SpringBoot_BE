package com.inkmelo.exception;

public class NoBookItemFoundException extends RuntimeException {
	
	public NoBookItemFoundException() {
		super();
	}
	
	public NoBookItemFoundException(String message) {
		super(message);
	}
	
	public NoBookItemFoundException(Integer id) {
		super("Tài nguyên sách với mã số " + id + " không tìm thấy trong cơ sở dữ liệu.");
	}
}
