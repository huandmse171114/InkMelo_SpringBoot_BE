package com.inkmelo.payment;

import lombok.Builder;

@Builder
public record PaymentResponseDTO(
			Long vnp_Amount,
			String vnp_BankCode,
			String vnp_BankTranNo,
			String vnp_CardType,
			String vnp_OrderInfo,
			String vnp_PayDate,
			String vnp_ResponseCode,
			String vnp_TmnCode,
			String vnp_TransactionNo,
			String vnp_TxnRef,
			String vnp_TransactionStatus,
			String vnp_SecureHash
		) {
	@Override
	public final String toString() {
	// TODO Auto-generated method stub
	return vnp_BankCode + "-" + vnp_CardType + "-" + vnp_OrderInfo();
	}
}
