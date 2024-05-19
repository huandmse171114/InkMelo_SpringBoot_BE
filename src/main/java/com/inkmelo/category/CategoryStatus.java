package com.inkmelo.category;

public enum CategoryStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private CategoryStatus(int value) {
		this.value = value;
	}
}
