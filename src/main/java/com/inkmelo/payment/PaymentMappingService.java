package com.inkmelo.payment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMappingService {
	public VNPayPaymentResponseDTO paymentToVNPayPaymentResponseDTO(VNPayPayment payment) {
		return VNPayPaymentResponseDTO.builder()
				.vnp_Amount(payment.getVnp_Amount())
				.vnp_BankCode(payment.getVnp_BankCode())
				.vnp_BankTranNo(payment.getVnp_BankTranNo())
				.vnp_CardType(payment.getVnp_CardType())
				.vnp_PayDate(payment.getVnp_PayDate())
				.vnp_ResponseCode(payment.getVnp_ResponseCode())
				.vnp_TmnCode(payment.getVnp_TmnCode())
				.vnp_TransactionNo(payment.getVnp_TransactionNo())
				.vnp_TxnRef(payment.getVnp_TxnRef())
				.vnp_TransactionStatus(payment.getVnp_TransactionStatus())
				.build();
	}
}
