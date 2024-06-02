package com.inkmelo.publisher;

public enum PublisherStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private PublisherStatus(int value) {
		this.value = value;
	}
}
