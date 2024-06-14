package com.inkmelo.exception;

public class NoCategoryFoundException extends RuntimeException {

		public NoCategoryFoundException() {
			super();
		}
		
		public NoCategoryFoundException(String message) {
			super(message);
		}
		
		public NoCategoryFoundException(Integer id) {
			super("Danh mục với mã số " + id + " không tìm thấy trong cơ sở dữ liệu.");
		}
}
