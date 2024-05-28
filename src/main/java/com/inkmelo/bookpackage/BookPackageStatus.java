package com.inkmelo.bookpackage;

public enum BookPackageStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private BookPackageStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
