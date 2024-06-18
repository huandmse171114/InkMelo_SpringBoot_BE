package com.inkmelo.bookpackage;

import java.util.EnumSet;
import java.util.Set;

public enum BookPackageMode {
	AUDIO(8, "Chỉ có sách Audio"),
	PDF(4, "Chỉ có sách PDF"),
	PAPER(1, "Chỉ có sách bản cứng"),
	AUDIOPDF(12, "Bao gồm sách Audio và PDF"),
	AUDIOPAPER(9, "Bao gồm sách Audio và bản cứng"),
	PDFPAPER(5, "Bao gồm sách PDF và bản cứng"),
	ALL(13, "Bao gồm sách Audio, PDF, và bản cứng");
	
	private final int value;
	private final String description;
	
	public static Set<BookPackageMode> allMode = EnumSet.of(
			AUDIO, PDF, PAPER, AUDIOPDF, AUDIOPAPER, PDFPAPER, ALL);
	
	private BookPackageMode(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return this.value;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static BookPackageMode fromValue(int value) {
		for (BookPackageMode b : BookPackageMode.values()) {
			if (b.value == value) {
				return b;
			}
		}
		
		throw new IllegalArgumentException("Giá trị của chế độ gói tài nguyên sách không tồn tại.");
	}
}
