package com.inkmelo.exception;

public class NoPublisherFoundException extends RuntimeException {
	
	public NoPublisherFoundException() {
		super();
	}
	
	public NoPublisherFoundException(String message) {
		super(message);
	}
	
	public NoPublisherFoundException(Integer id) {
		super("Nhà xuất bản với mã số " + id + " không tìm thấy trong cơ sở dữ liệu.");
	}
	
}
