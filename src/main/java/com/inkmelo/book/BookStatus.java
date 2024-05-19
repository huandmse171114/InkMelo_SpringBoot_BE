package com.inkmelo.book;

public enum BookStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private BookStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
