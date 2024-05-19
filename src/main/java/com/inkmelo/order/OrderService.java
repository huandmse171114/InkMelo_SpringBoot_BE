package com.inkmelo.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
	private OrderRepository repository;

	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}
	
	
}
