package com.inkmelo.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.inkmelo.utils.GlobalVariable;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService service;
	
	@GetMapping("/store/api/v1/payment")
	public RedirectView getPaymentResponse(
		@RequestParam("vnp_Amount")	Long vnp_Amount,
		@RequestParam("vnp_BankCode")	String vnp_BankCode,
		@RequestParam("vnp_BankTranNo")	String vnp_BankTranNo,
		@RequestParam("vnp_CardType")	String vnp_CardType,
		@RequestParam("vnp_OrderInfo")	String vnp_OrderInfo,
		@RequestParam("vnp_PayDate")	String vnp_PayDate,
		@RequestParam("vnp_ResponseCode")	String vnp_ResponseCode,
		@RequestParam("vnp_TmnCode")	String vnp_TmnCode,
		@RequestParam("vnp_TransactionNo")	String vnp_TransactionNo,
		@RequestParam("vnp_TxnRef")	String vnp_TxnRef,
		@RequestParam("vnp_TransactionStatus")	String vnp_TransactionStatus,
		@RequestParam("vnp_SecureHash")	String vnp_SecureHash
			) {
		System.out.println(vnp_Amount);
		System.out.println(vnp_BankCode);
		System.out.println(vnp_BankTranNo);
		System.out.println(vnp_CardType);
		System.out.println(vnp_OrderInfo);
		System.out.println(vnp_PayDate);
		System.out.println(vnp_ResponseCode);
		System.out.println(vnp_TmnCode);
		System.out.println(vnp_TransactionNo);
		System.out.println(vnp_TxnRef);
		System.out.println(vnp_TransactionStatus);
		System.out.println(vnp_SecureHash);
		System.out.println(GlobalVariable.getRedirectURL());
		
        return new RedirectView(GlobalVariable.getRedirectURL());
	}
	
}
