package com.inkmelo.book;

import java.util.EnumSet;
import java.util.Set;

public enum BookStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	public static Set<BookStatus> allStatus = EnumSet.of(ACTIVE, INACTIVE);
	
	private BookStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
