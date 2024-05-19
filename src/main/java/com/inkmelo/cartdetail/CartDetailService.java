package com.inkmelo.cartdetail;

import org.springframework.stereotype.Service;

@Service
public class CartDetailService {
	private CartDetailRepository repository;

	public CartDetailService(CartDetailRepository repository) {
		this.repository = repository;
	}
	
	
}
