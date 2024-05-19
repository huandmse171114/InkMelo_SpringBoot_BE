package com.inkmelo.order;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
	private OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}
	
	
}
