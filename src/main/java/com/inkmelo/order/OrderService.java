package com.inkmelo.order;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inkmelo.VnPay.VnPayService;
import com.inkmelo.cartdetail.CartDetail;
import com.inkmelo.cartdetail.CartDetailCreateUpdateBodyDTO;
import com.inkmelo.cartdetail.CartDetailRepository;
import com.inkmelo.cartdetail.CartDetailService;
import com.inkmelo.cartdetail.CartDetailStatus;
import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.exception.BookPackageOutOfStockException;
import com.inkmelo.exception.NoCartDetailFoundException;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoOrderFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.orderdetail.OrderDetail;
import com.inkmelo.orderdetail.OrderDetailMappingService;
import com.inkmelo.orderdetail.OrderDetailRepository;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.utils.Utils;

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
	private final CartDetailRepository cartDetailRepository;
	private final CartDetailService cartDetailService;
	private final OrderDetailRepository orderDetailRepository;
	private final int DEFAULT_PAGE = 0;
	private final int DEFALT_SIZE = 5;

	public String saveOrder(HttpServletRequest req, @Valid OrderCreateBodyDTO orderDTO, String username) throws Exception {
		
		Customer customer = getCustomer(username);
		
		Order order = mappingService.orderCreateBodyDTOToOrder(orderDTO);
		
		List<CartDetail> cartDetails = cartDetailRepository.findAllByStatusAndIdIn(CartDetailStatus.ACTIVE, orderDTO.items());
		
		if (cartDetails.size() < orderDTO.items().size()) {
			System.out.println("error found");
			throw new NoCartDetailFoundException("Sản phẩm cần thanh toán không có trong giỏ hàng");
		}
		
		order.setCustomer(customer);
		
		// luu don thanh toan o trang thai cho thanh toan
		Order orderDB = repository.save(order);
		
//		Kiem tra so luong ton kho cua book package va luu order detail
		cartDetails.forEach(detail -> {
			if (detail.getQuantity() > detail.getBookPackage().getStock()) {
				throw new BookPackageOutOfStockException("Số lượng tồn kho cuả sản phẩm " + detail.getBookPackage().getTitle() + " không đủ.");
			}
			
			OrderDetail orderDetail = detailMappingService.cartDetailToOrderDetail(detail);
			orderDetail.setOrder(orderDB);
			orderDetailRepository.save(orderDetail);
			
		});
		
		// lay redirect url de chuyen sang trang thanh toan cua vnpay
		String paymentUrl = vnpayService.getPaymentUrl(req, order.getTotalPrice(), orderDB.getId(), orderDTO.redirectUrl());
		System.out.println(paymentUrl);
		
		return paymentUrl;
	}
	
	public ResponseEntity<?> findAllOrdersByCustomer(String username, OrderStatus status, Integer page, Integer size,
			String fromDateStr, String toDateStr) {
//		Kiem tra nguoi dung co ton tai hay khong
		Customer customer = getCustomer(username);
		System.out.println(customer.getEmail());
		LocalDate fromDate;
		LocalDate toDate;
		
		if (page == null) page = DEFAULT_PAGE;
		if (size == null) size = DEFALT_SIZE;
		
		Pageable paging = PageRequest.of(page, size);
		
		if (fromDateStr.isEmpty()) {
			LocalDate todayDate = LocalDate.now();
//			Neu nguoi dung khong nhap ngay cu the, lay ngay dau tien trong thang
			fromDate = todayDate.withDayOfMonth(1);
			System.out.println(fromDate.toString());
		}else {
			fromDate = LocalDate.parse(fromDateStr);			
			System.out.println(fromDate.toString());
		}
		
		
		if (toDateStr.isEmpty()) {
			toDate = LocalDate.now();
			System.out.println(toDate.toString());
		}else {
			toDate = LocalDate.parse(toDateStr);
			System.out.println(toDate.toString());
		}
		
		Page<Order> pageOrders = repository
				.findAllByCustomerAndStatusAndCreatedAtBetweenOrderByCreatedAtDesc(
						customer, 
						status,
						fromDate,
						toDate,
						paging);
		
		List<Order> orders = pageOrders.getContent();
		
		if (orders.size() == 0) {
			throw new NoOrderFoundException("Không tìm thấy đơn hàng.");
		}
		
		var response = orders.stream()
				.map(order -> mappingService.orderToOrderResponseDTO(order))
				.toList();
		
		return Utils.generatePagingListResponseEntity(
				pageOrders.getTotalElements(), 
				response, 
				pageOrders.getTotalPages(), 
				pageOrders.getNumber(), 
				HttpStatus.OK);
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
	
}
