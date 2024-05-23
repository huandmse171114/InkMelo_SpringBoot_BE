package com.inkmelo.cartdetail;

public enum CartDetailStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private CartDetailStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
