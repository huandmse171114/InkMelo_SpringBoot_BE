package com.inkmelo.customer;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	private CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}
	
}
