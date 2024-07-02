package com.inkmelo.payment;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoOrderFoundException;
import com.inkmelo.order.Order;
import com.inkmelo.order.OrderRepository;
import com.inkmelo.order.OrderStatus;
import com.inkmelo.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final VNPayPaymentRepository vnpayRepository;
	private final OrderRepository orderRepository;

	public String handleVNPayResponse(Long vnp_Amount, String vnp_BankCode, String vnp_BankTranNo, String vnp_CardType,
			String vnp_OrderInfo, String vnp_PayDate, String vnp_ResponseCode, String vnp_TmnCode,
			String vnp_TransactionNo, String vnp_TxnRef, String vnp_TransactionStatus, String vnp_SecureHash) {
		
		
		Integer orderId = Integer.parseInt(vnp_OrderInfo.substring(20,21));
		String[] infoSplit = vnp_OrderInfo.split("[|]");
		String redirectURL = "";
		if (infoSplit.length > 1) {
			redirectURL = infoSplit[1];
		}
		
		Optional<Order> orderOptional = orderRepository.findById(orderId);
		
		if (orderOptional.isEmpty()) {
			throw new NoOrderFoundException("Không tìm thấy đơn hàng vừa thanh toán.");
		}
		
		Order order = orderOptional.get();
		
		VNPayPayment vnpayPayment = VNPayPayment.builder()
				.createdDate(Utils.stringToTimestamp(vnp_PayDate))
				.vnp_Amount(vnp_Amount)
				.vnp_BankCode(vnp_BankCode)
				.vnp_BankTranNo(vnp_BankTranNo)
				.vnp_CardType(vnp_CardType)
				.vnp_OrderInfo(vnp_OrderInfo)
				.vnp_PayDate(vnp_PayDate)
				.vnp_ResponseCode(vnp_ResponseCode)
				.vnp_SecureHash(vnp_SecureHash)
				.vnp_TmnCode(vnp_TmnCode)
				.vnp_TransactionNo(vnp_TransactionNo)
				.vnp_TransactionStatus(vnp_TransactionStatus)
				.vnp_TxnRef(vnp_TxnRef)
				.status(PaymentStatus.ACTIVE)
				.paymentMethod(PaymentMethod.VNPAY)
				.order(order)
				.build();
		
		if (vnp_ResponseCode.equals("00")) {
			order.setStatus(OrderStatus.FINISHED);			
		}else {
			order.setStatus(OrderStatus.PAYMENT_FAILED);
		}
		
		vnpayRepository.save(vnpayPayment);
		
		orderRepository.save(order);
		
		return redirectURL;
	}
	
	
}
