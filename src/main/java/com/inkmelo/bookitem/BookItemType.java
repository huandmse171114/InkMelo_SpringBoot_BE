package com.inkmelo.bookitem;

import java.util.EnumSet;
import java.util.Set;

public enum BookItemType {
	PAPER,
	AUDIO,
	PDF;
	
	public static Set<BookItemType> allType = EnumSet.of(PAPER, AUDIO, PDF);
}
