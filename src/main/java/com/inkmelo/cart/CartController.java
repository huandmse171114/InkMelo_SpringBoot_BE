package com.inkmelo.cart;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
	
	private CartService service;

	public CartController(CartService service) {
		this.service = service;
	}
	
	
}
