package com.inkmelo.exception;

public class NoGenreFoundException extends RuntimeException {
	
	public NoGenreFoundException() {
		super();
	}
	
	public NoGenreFoundException(String message) {
		super(message);
	}
	
	public NoGenreFoundException(Integer id) {
		super("Thể loại sách với mã số " + id + " không tìm thấy trong cơ sở dữ liệu.");
	}
	
}
