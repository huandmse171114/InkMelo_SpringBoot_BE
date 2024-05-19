package com.inkmelo.orderdetail;

public enum OrderDetailType {
	COMBO(1),
	SINGLE(0);
	
	public final int value;
	
	private OrderDetailType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
}
