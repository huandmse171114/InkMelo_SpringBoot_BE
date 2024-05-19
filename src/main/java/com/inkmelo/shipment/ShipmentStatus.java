package com.inkmelo.shipment;

public enum ShipmentStatus {
	ACTIVE(1),
	INACTIVE(0);
	
	public final int value;
	
	private ShipmentStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
