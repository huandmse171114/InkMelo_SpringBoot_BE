package com.inkmelo.order;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inkmelo.VnPay.VnPayService;
import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.ghn.GHNApis;
import com.inkmelo.orderdetail.OrderDetail;
import com.inkmelo.orderdetail.OrderDetailMappingService;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository repository;
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final OrderDetailMappingService detailMappingService;
	private final OrderMappingService mappingService;
	private final VnPayService vnpayService;
	private final GHNApis ghnApis;

	public String saveOrder(HttpServletRequest req, @Valid OrderCreateBodyDTO orderDTO, String username) throws Exception {
		
		Customer customer = getCustomer(username);
		
		Order order = mappingService.orderCreateBodyDTOToOrder(orderDTO);
		
		List<OrderDetail> orderDetails = orderDTO.items().stream()
				.map(item -> detailMappingService.cartDetailResponseDTOToOrderDetail(item))
				.toList();

		order.setCustomer(customer);
		order.setOrderDetails(orderDetails);
		
		// Tinh toan thoi gian giao hang du kien
		Date expectedDeliveryTime = ghnApis.calculateExpectedDeliveryTime(order.getShipmentDistrictId(), order.getShipmentWardCode());
		order.setExpectedDeliveryTime(expectedDeliveryTime);
		
		// Tinh toan chi phi giao hang
		
		// luu don thanh toan o trang thai cho thanh toan
		Order orderDB = repository.save(order);
		// lay redirect url de chuyen sang trang thanh toan cua vnpay
		String paymentUrl = vnpayService.getPaymentUrl(req, order.getTotalPrice(), orderDB.getId(), orderDTO.redirectUrl());
		System.out.println(paymentUrl);
		
		return paymentUrl;
	}
	
	private Customer getCustomer(String username) {
		
		Optional<User> userOption = userRepository.findByUsername(username);
		
		if (userOption.isEmpty()) {
			throw new NoUserFoundException("Người dùng không tồn tại.");
		}
		
		Optional<Customer> customerOption = customerRepository.findByUser(userOption.get());
		
		if (customerOption.isEmpty()) {
			throw new NoCustomerFoundException("Khách hàng không tồn tại.");
		}
		
		return customerOption.get();
	}

	public ResponseEntity<?> findAllOrdersByCustomer(String username, OrderStatus finished) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
