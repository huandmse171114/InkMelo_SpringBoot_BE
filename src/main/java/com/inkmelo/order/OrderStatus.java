package com.inkmelo.order;

public enum OrderStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private OrderStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
