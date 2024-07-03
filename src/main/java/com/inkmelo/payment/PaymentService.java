package com.inkmelo.payment;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkmelo.exception.NoOrderFoundException;
import com.inkmelo.ghn.GHNApis;
import com.inkmelo.ghn.GHNOrderResponse;
import com.inkmelo.mailsender.SendEmailService;
import com.inkmelo.order.Order;
import com.inkmelo.order.OrderRepository;
import com.inkmelo.order.OrderStatus;
import com.inkmelo.utils.Utils;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final VNPayPaymentRepository vnpayRepository;
	private final OrderRepository orderRepository;
	private final GHNApis ghnApis;
	private final SendEmailService emailService;

	public String handleVNPayResponse(Long vnp_Amount, String vnp_BankCode, String vnp_BankTranNo, String vnp_CardType,
			String vnp_OrderInfo, String vnp_PayDate, String vnp_ResponseCode, String vnp_TmnCode,
			String vnp_TransactionNo, String vnp_TxnRef, String vnp_TransactionStatus, String vnp_SecureHash) throws MessagingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
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
				.build();
		
		if (vnp_ResponseCode.equals("00")) {
			order.setStatus(OrderStatus.FINISHED);			
		}else {
			order.setStatus(OrderStatus.PAYMENT_FAILED);
		}
		
		VNPayPayment vnpayPaymentDB = vnpayRepository.save(vnpayPayment);
		order.setPayment(vnpayPaymentDB);
		
		String ghnOrderResponse = ghnApis.createOrder(
				order.getShipmentDistrictId(), 
				order.getShipmentWardCode(), 
				2, 
				order.getReceiverName(), 
				order.getContactNumber(), 
				order.getShipmentStreet(),
				order.getGhnServiceId());
		
		System.out.println(ghnOrderResponse);
		
		GHNOrderResponse orderResponse = objectMapper.readValue(ghnOrderResponse, GHNOrderResponse.class);
		var currentDate = Date.valueOf(LocalDate.now()).getTime();
		order.setExpectedDeliveryTime(orderResponse.getData().getExpected_delivery_time());
		order.setExpectedDaysToDelivery((order.getExpectedDeliveryTime().getTime() - currentDate) / 
				(1000 * 60 * 60 * 24) );
		order.setGhbOrderCode(orderResponse.getData().getOrder_code());
		orderRepository.save(order);
		
		emailService.sendConfirmEmail(order.getCustomer().getEmail(), "Đơn hàng của bạn đã thanh toán thành công", "XÁC NHẬN THANH TOÁN THÀNH CÔNG");
		
		return redirectURL;
	}
	
	
}
