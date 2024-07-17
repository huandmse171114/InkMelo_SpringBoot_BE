package com.inkmelo.order;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoCartDetailFoundException;
import com.inkmelo.exception.NoCategoryFoundException;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoOrderFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {
	private final OrderService service;
	
	@PostMapping("/store/api/v1/customer/{username}/orders")
	public ResponseEntity<?> saveOrder(
				HttpServletRequest req,
				@PathVariable("username") String username,
				@Valid @RequestBody OrderCreateBodyDTO orderDTO
			) {
		String paymentUrl = "";
		try {
			paymentUrl = service.saveOrder(req, orderDTO, username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		var response = new HashMap<String, Object>();
		response.put("paymentUrl", paymentUrl);
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/admin/api/v1/orders")
	public ResponseEntity<?> getAllOrders(
				@RequestParam(name = "page", required = false) Integer page,
				@RequestParam(name = "size", required = false) Integer size,
				@RequestParam(name = "username", required = false) String username
			) {
		
		return null;
	}
	
	@GetMapping("/store/api/v1/customers/{username}/orders")
	public ResponseEntity<?> getAllOrdersByCustomer(
				@PathVariable(name = "username") String username,
				@RequestParam(required = false, defaultValue = "0") Integer page,
				@RequestParam(required = false, defaultValue = "5") Integer size,
				@RequestParam(required = false, defaultValue = "2024-01-01") String fromDate,
				@RequestParam(required = false, defaultValue = "2024-12-24") String toDate
			) {
		if (fromDate == null) fromDate = "";
		if (toDate == null) toDate = "";
		
		return service.findAllOrdersByCustomer(username, OrderStatus.PAYMENT_FINISHED, page, size, fromDate, toDate);
	}
	
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoOrderFoundException.class)
	public ResponseEntity<?> handleNoOrderFoundException(
			NoOrderFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoUserFoundException.class)
	public ResponseEntity<?> handleNoUserFoundException(
			NoUserFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCustomerFoundException.class)
	public ResponseEntity<?> handleNoCustomerFoundException(
			NoCustomerFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<?> handleDateTimeParseExceptionn(
			DateTimeParseException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				"Định dạng ngày không hợp lệ (yyy-MM-dd).", 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoCartDetailFoundException.class)
	public ResponseEntity<?> handleNoCartDetailFoundException(
			NoCartDetailFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				"Định dạng ngày không hợp lệ (yyy-MM-dd).", 
				HttpStatus.BAD_REQUEST);
	}
	
	
}
