package com.inkmelo.orderdetail;

public enum OrderDetailStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private OrderDetailStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
}
