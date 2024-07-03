package com.inkmelo.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer Management APIs")
public class CustomerController {
	private final CustomerService service;
	
	
	@Operation(summary = "Get Customer Profile",
			description = "This endpoint will return customer profile information base on username | (Authority) CUSTOMER.")
	@GetMapping("/store/api/v1/customers/{username}")
	public ResponseEntity<?> getCustomerProfile(@PathVariable("username") String username) {
		return service.findCustomerByUsername(username);
	}
	
	@Operation(summary = "Update Customer Profile",
			description = "This endpoint will update customer profile information base on username | (Authority) ALL.")
	@PutMapping("/store/api/v1/customers/{username}")
	public ResponseEntity<?> updateCustomerProfile(@PathVariable("username") String username,
			@Valid @RequestBody CustomerProfileUpdateBodyDTO customerDTO) {
		service.updateCustomerProfile(username, customerDTO);
		
		return Utils.generateMessageResponseEntity("Cập nhật thông tin cá nhân thành công", HttpStatus.OK);
	}
	
//	====================================== Exception Handler ===================================
	
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
	
}
