package com.inkmelo.cartdetail;

public enum CartDetailType {
	COMBO(1),
	SINGLE(0);
	
	public final int value;
	
	private CartDetailType(int value) {
		this.value = value;
	}
}
