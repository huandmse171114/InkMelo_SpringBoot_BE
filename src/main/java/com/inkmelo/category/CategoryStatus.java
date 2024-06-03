package com.inkmelo.category;

import java.util.EnumSet;
import java.util.Set;

public enum CategoryStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	public static Set<CategoryStatus> allStatus = EnumSet.of(ACTIVE, INACTIVE);
	
	private CategoryStatus(int value) {
		this.value = value;
	}
}
