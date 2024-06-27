package com.inkmelo.order;

public enum OrderStatus {
	PAYMENT_PENDING(1),
	FINISHED(0);
	
	public final int value;
	
	private OrderStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
