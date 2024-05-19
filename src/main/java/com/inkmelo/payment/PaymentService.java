package com.inkmelo.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	private PaymentRepository repository;

	public PaymentService(PaymentRepository repository) {
		this.repository = repository;
	}
	
	
}
