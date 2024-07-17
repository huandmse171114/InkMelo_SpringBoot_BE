package com.inkmelo.payment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMappingService {
	public VNPayPaymentResponseDTO paymentToVNPayPaymentResponseDTO(Payment payment) {
		return VNPayPaymentResponseDTO.builder()
				.build();
	}
}
