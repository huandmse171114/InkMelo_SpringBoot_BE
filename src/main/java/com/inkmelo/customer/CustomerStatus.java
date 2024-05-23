package com.inkmelo.customer;

public enum CustomerStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private CustomerStatus(int value) {
		this.value = value;
	}
}
