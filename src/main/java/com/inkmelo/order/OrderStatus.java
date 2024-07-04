package com.inkmelo.order;

public enum OrderStatus {
	PAYMENT_PENDING(1),
	PAYMENT_FINISHED(0),
	PAYMENT_FAILED(2);
	
	public final int value;
	
	private OrderStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
