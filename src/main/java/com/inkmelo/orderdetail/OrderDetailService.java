package com.inkmelo.orderdetail;

import org.springframework.stereotype.Service;


@Service
public class OrderDetailService {
	private OrderDetailRepository repository;

	public OrderDetailService(OrderDetailRepository repository) {
		this.repository = repository;
	}
	
	
}
