package com.inkmelo.cart;

import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.cartdetail.CartDetailService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cart", description = "Cart Management APIs")
@RestController
@RequiredArgsConstructor
public class CartController {
	
	private final CartService service;
	private final CartDetailService cartDetailService;
	
	
	
	
	
}
