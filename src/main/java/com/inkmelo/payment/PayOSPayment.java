package com.inkmelo.payment;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayOSPayment extends Payment{
	private Long pos_Amount;
	private	String pos_BankCode;
	private	String pos_BankTranNo;
	private	String pos_CardType;
	private	String pos_OrderInfo;
	private	String pos_PayDate;
}
