package com.inkmelo.payment;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class VNPayPayment extends Payment{
	private Long vnp_Amount;
	private	String vnp_BankCode;
	private	String vnp_BankTranNo;
	private	String vnp_CardType;
	private	String vnp_OrderInfo;
	private	String vnp_PayDate;
	private	String vnp_ResponseCode;
	private	String vnp_TmnCode;
	private	String vnp_TransactionNo;
	private	String vnp_TxnRef;
	private	String vnp_TransactionStatus;
	private	String vnp_SecureHash;
}
