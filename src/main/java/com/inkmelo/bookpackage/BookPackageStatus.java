package com.inkmelo.bookpackage;

import java.util.EnumSet;
import java.util.Set;

public enum BookPackageStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	public static Set<BookPackageStatus> allStatus = EnumSet.of(ACTIVE, INACTIVE);
	
	private BookPackageStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
