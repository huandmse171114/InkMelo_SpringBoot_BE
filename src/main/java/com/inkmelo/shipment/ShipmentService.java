package com.inkmelo.shipment;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoShipmentExistException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.utils.Utils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {
	private final ShipmentRepository repository;
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final ShipmentMappingService mappingService;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;

	public ResponseEntity<?> findAllShipmentByStatus(ShipmentStatus status, String username, Integer page,
			Integer size) {
		
		Customer customer = getCustomer(username);
		
//		Get shipment, no paging
		if (page == null & size == null) {
			
			var shipments = repository.findAllByStatusAndCustomer(status, customer);
			
			if (shipments.isEmpty()) {
				throw new NoShipmentExistException("Dữ liệu về thông tin giao hàng hiện đang rỗng");
			}
			
			var response = shipments.stream()
					.map(shipment -> mappingService.shipmentToShipmentResponseDTO(shipment))
					.toList();
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
//		Get shipment, with paging
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			var pageShipments = repository.findAllByStatusAndCustomer(status, customer, paging);
			
			var shipments = pageShipments.getContent();
			
			if (shipments.isEmpty()) {
				throw new NoShipmentExistException("Dữ liệu về thông tin giao hàng hiện đang rỗng");
			}
			
			var response = shipments.stream()
					.map(shipment -> mappingService.shipmentToShipmentResponseDTO(shipment))
					.toList();
			
			return Utils.generatePagingListResponseEntity(
					pageShipments.getTotalElements(), 
					response, 
					pageShipments.getTotalPages(), 
					pageShipments.getNumber(), 
					HttpStatus.OK);
		}
		
	}

	public void saveShipment(String username, @Valid ShipmentCreateBodyDTO shipmentDTO) {
		
		Customer customer = getCustomer(username);
		
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
