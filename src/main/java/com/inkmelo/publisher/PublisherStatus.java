package com.inkmelo.publisher;

import java.util.EnumSet;
import java.util.Set;

public enum PublisherStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public static Set<PublisherStatus> allStatus = EnumSet.of(ACTIVE, INACTIVE);
	
	public final int value;
	
	private PublisherStatus(int value) {
		this.value = value;
	}
	
	
}
