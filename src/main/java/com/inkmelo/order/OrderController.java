package com.inkmelo.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		
		return Utils.generateMessageResponseEntity(paymentUrl, HttpStatus.CREATED);
	}
	
}
