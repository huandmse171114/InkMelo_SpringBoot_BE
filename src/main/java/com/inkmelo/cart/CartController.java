package com.inkmelo.cart;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cart", description = "Cart Management APIs")
@RestController
public class CartController {
	
	private CartService service;

	public CartController(CartService service) {
		this.service = service;
	}
	
	
}
