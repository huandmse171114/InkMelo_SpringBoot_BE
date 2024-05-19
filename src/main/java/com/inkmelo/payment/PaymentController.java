package com.inkmelo.payment;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
	private PaymentService service;

	public PaymentController(PaymentService service) {
		this.service = service;
	}
	
	
}
