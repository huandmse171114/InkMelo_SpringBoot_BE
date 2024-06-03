package com.inkmelo.genre;

import java.util.EnumSet;
import java.util.Set;

public enum GenreStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	private final int value;
	
	public static Set<GenreStatus> allStatus = EnumSet.of(ACTIVE, INACTIVE);
	
	private GenreStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
	
}
