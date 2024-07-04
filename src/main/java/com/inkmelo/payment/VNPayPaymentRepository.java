package com.inkmelo.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VNPayPaymentRepository extends JpaRepository<VNPayPayment, Integer> {
	
}
