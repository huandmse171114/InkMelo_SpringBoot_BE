package com.inkmelo.bookrating;

public enum BookRatingStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private BookRatingStatus(int value) {
		this.value = value;
	}
}
