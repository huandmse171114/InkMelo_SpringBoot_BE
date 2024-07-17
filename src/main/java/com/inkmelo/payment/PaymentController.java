package com.inkmelo.payment;

import java.io.IOException;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RestController
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService service;
	
	@GetMapping("/store/api/v1/payment/vnpay/response")
	public Object getVNPayPaymentResponse(
		ModelMap model,
		@RequestParam(name = "vnp_Amount", required = false)	Long vnp_Amount,
		@RequestParam(name = "vnp_BankCode", required = false)	String vnp_BankCode,
		@RequestParam(name = "vnp_BankTranNo", required = false)	String vnp_BankTranNo,
		@RequestParam(name = "vnp_CardType", required = false)	String vnp_CardType,
		@RequestParam(name = "vnp_OrderInfo", required = false)	String vnp_OrderInfo,
		@RequestParam(name = "vnp_PayDate", required = false)	String vnp_PayDate,
		@RequestParam(name = "vnp_ResponseCode", required = false)	String vnp_ResponseCode,
		@RequestParam(name = "vnp_TmnCode", required = false)	String vnp_TmnCode,
		@RequestParam(name = "vnp_TransactionNo", required = false)	String vnp_TransactionNo,
		@RequestParam(name = "vnp_TxnRef", required = false)	String vnp_TxnRef,
		@RequestParam(name = "vnp_TransactionStatus", required = false)	String vnp_TransactionStatus,
		@RequestParam(name = "vnp_SecureHash", required = false)	String vnp_SecureHash
			) throws MessagingException, IOException {
		
		String redirectUrl = service.handleVNPayResponse(vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType, vnp_OrderInfo,
				vnp_PayDate, vnp_ResponseCode, vnp_TmnCode, vnp_TransactionNo, vnp_TxnRef,
				vnp_TransactionStatus, vnp_SecureHash);
		
		model.addAttribute("message", "<h3>Hello Thymeleaf</h3>");
		
		if (redirectUrl.isEmpty()) {
			return "vnpay_return";			
		}else {
			return new RedirectView("/payment-success.html");
		}
		
	}
	
}
