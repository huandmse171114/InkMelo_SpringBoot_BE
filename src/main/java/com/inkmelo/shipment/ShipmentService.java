package com.inkmelo.shipment;

import org.springframework.stereotype.Service;

@Service
public class ShipmentService {
	private ShipmentRepository repository;

	public ShipmentService(ShipmentRepository repository) {
		this.repository = repository;
	}
	
	
}
