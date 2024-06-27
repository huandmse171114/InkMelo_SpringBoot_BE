package com.inkmelo.customer;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerMappingService {
	
	public Customer userToCustomer(User user) {
		return Customer.builder()
				.fullname(user.getFullname())
				.email(user.getEmail())
				.user(user)
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastChangedBy(SecurityContextHolder.getContext()
						.getAuthentication().getName())
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.build();
	}
	
	public CustomerProfileResponseDTO customerToCustomerProfileResponseDTO(Customer customer) {
		return CustomerProfileResponseDTO.builder()
				.fullname(customer.getFullname())
				.dateOfBirth(customer.getDateOfBirth())
				.gender(customer.getGender())
				.phone(customer.getPhone())
				.email(customer.getEmail())
				.profileImg(customer.getProfileImg())
				.lastUpdatedTime(customer.getLastUpdatedTime())
				.build();
	}
}
