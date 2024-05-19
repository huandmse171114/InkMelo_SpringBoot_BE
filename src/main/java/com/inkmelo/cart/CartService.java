package com.inkmelo.cart;

import org.springframework.stereotype.Service;

@Service
public class CartService {
	
	private CartRepository repository;

	public CartService(CartRepository repository) {
		this.repository = repository;
	}
	
	
}
