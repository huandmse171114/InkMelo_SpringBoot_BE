package com.inkmelo.bookcombo;

public enum BookComboStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private BookComboStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
