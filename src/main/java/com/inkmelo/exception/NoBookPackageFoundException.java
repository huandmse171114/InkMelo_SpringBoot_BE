package com.inkmelo.exception;

public class NoBookPackageFoundException extends RuntimeException{
	
	public NoBookPackageFoundException() {
		super();
	}
	
	public NoBookPackageFoundException(String message) {
		super(message);
	}
	
	public NoBookPackageFoundException(int id) {
		super("Gói tài nguyên sách với mã số " + id + " không tìm thấy trong cơ sở dữ liệu.");
	}
}
