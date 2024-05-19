package com.inkmelo.genre;

public enum GenreStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private GenreStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
