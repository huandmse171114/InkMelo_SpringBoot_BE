package com.inkmelo.bookitem;

import java.util.EnumSet;
import java.util.Set;

public enum BookItemStatus {
	ACTIVE,
	INACTIVE;
	
	public static Set<BookItemStatus> allStatus = EnumSet.of(ACTIVE, INACTIVE);
}
