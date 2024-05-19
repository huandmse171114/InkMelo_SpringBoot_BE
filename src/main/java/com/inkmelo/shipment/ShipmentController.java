package com.inkmelo.shipment;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShipmentController {
	private ShipmentService service;

	public ShipmentController(ShipmentService service) {
		this.service = service;
	}
	
}
