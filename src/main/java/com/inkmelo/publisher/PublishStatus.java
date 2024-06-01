package com.inkmelo.publisher;

public enum PublishStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private PublishStatus(int value) {
		this.value = value;
	}
}
