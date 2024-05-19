package com.inkmelo.user;

public enum UserStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private UserStatus(int value) {
		this.value = value;
	}
}
