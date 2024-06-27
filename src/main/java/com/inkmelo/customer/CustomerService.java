package com.inkmelo.customer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository repository;
	private final UserRepository userRepository;
	private final CustomerMappingService mappingService;
	
	
	public ResponseEntity<?> findCustomerByUsername(String username) {
		Optional<User> userOption = userRepository.findByUsername(username);
		
		if (userOption.isEmpty()) throw new NoUserFoundException("Tài khoản không tồn tại.");
		
		Optional<Customer> customerOption = repository.findByUser(userOption.get());
		
		if (customerOption.isEmpty()) throw new NoCustomerFoundException("Khách hàng không tồn tại.");
		
		Customer customer = customerOption.get();
		
		return new ResponseEntity<>(mappingService.customerToCustomerProfileResponseDTO(customer), HttpStatus.OK);
	}


	public void updateCustomerProfile(String username, CustomerProfileUpdateBodyDTO customerDTO) {
		Optional<User> userOption = userRepository.findByUsername(username);
		
		if (userOption.isEmpty()) throw new NoUserFoundException("Tài khoản không tồn tại.");
		
		Optional<Customer> customerOption = repository.findByUser(userOption.get());
		
		if (customerOption.isEmpty()) throw new NoCustomerFoundException("Khách hàng không tồn tại.");
		
		Customer customer = customerOption.get();
		
		customer.setFullname(customerDTO.fullname());
		customer.setDateOfBirth(customerDTO.dateOfBirth());
		customer.setGender(customerDTO.gender());
		customer.setPhone(customerDTO.phone());
		customer.setEmail(customerDTO.email());
		customer.setProfileImg(customerDTO.profileImg());
		customer.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		customer.setLastChangedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		
		repository.save(customer);
	}
	
	
}
