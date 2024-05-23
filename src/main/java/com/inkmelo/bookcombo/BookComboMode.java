package com.inkmelo.bookcombo;

public enum BookComboMode {
	AUDIO(8),
	PDF(4),
	PAPER(1),
	AUDIOPDF(12),
	AUDIOPAPER(9),
	PDFPAPER(5),
	ALL(13);
	
	public final int value;
	
	private BookComboMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
