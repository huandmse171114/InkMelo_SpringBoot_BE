package com.inkmelo.payment;

public enum PaymentStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private PaymentStatus(int value) {
		this.value = value;
	}
}
