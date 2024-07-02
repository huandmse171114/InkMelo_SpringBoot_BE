package com.inkmelo.shipment;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.exception.DefaultShipmentUpdateException;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoShipmentExistException;
import com.inkmelo.exception.NoShipmentFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.ghn.GHNApis;
import com.inkmelo.ghn.GHNProvinceResponseDTO;
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
		
		Shipment shipment = mappingService.shipmentCreateBodyDTOToShipment(shipmentDTO);
		
		if (shipment.isDefault()) {
			Optional<Shipment> defaultShipmentOptional = repository.findByCustomerAndIsDefault(customer, true);
			
			if (!defaultShipmentOptional.isEmpty()) {
				Shipment defaultShipment = defaultShipmentOptional.get();
				defaultShipment.setDefault(false);
				defaultShipment.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
				repository.save(defaultShipment);
			}			
		}
		
		shipment.setCustomer(customer);
		
		repository.save(shipment);
	}
	
	

	public void updateShipment(String username, ShipmentUpdateBodyDTO shipmentDTO) {
		Customer customer = getCustomer(username);
		
		Optional<Shipment> shipmentOption = repository.findById(shipmentDTO.id());
		
		if (shipmentOption.isEmpty()) {
			throw new NoShipmentFoundException("Không tìm thấy thông tin vận chuyển cần chỉnh sửa");
		}
		
		Shipment shipment = shipmentOption.get();

//		Need to update default shipment
		if (shipment.isDefault() != shipmentDTO.isDefault()) {
//			Update to be default shipment
			if (shipmentDTO.isDefault()) {
				Optional<Shipment> defaultShipmentOptional = repository.findByCustomerAndIsDefault(customer, true);
				
//				Check if default shipment existed
				if (!defaultShipmentOptional.isEmpty()) {
					Shipment defaultShipment = defaultShipmentOptional.get();
					defaultShipment.setDefault(false);
					defaultShipment.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
					repository.save(defaultShipment);
				}
//			Update to not be default shipment
			}else {
				Optional<Shipment> newDefaultShipmentOptional = repository.findByCustomerAndIdNot(customer, shipment.getId());
				
//				If there is one another shipment
				if (newDefaultShipmentOptional.isPresent()) {
					Shipment newDefaultShipment = newDefaultShipmentOptional.get();
					newDefaultShipment.setDefault(true);
					newDefaultShipment.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
					repository.save(newDefaultShipment);
//				If there is not another shipment
				}else {
					throw new DefaultShipmentUpdateException("Không thể thay đổi địa chỉ mặc định. Địa chỉ này là duy nhất.");
				}
			}
		}
		
		shipment.setReceiverName(shipmentDTO.receiverName());
		shipment.setContactNumber(shipmentDTO.contactNumber());
		shipment.setDescription(shipmentDTO.description());
		shipment.setStreet(shipmentDTO.street());
		shipment.setWard(shipmentDTO.ward());
		shipment.setWardCode(shipmentDTO.wardCode());
		shipment.setDistrict(shipmentDTO.district());
		shipment.setDistrictId(shipmentDTO.districtId());
		shipment.setProvince(shipmentDTO.province());
		shipment.setProvinceId(shipmentDTO.provinceId());
		shipment.setDefault(shipmentDTO.isDefault());
		shipment.setStatus(shipmentDTO.status());
		shipment.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		
		repository.save(shipment);
	}
	
	public void deleteShipment(String username, Integer id) {
		
		Customer customer = getCustomer(username);
		
		Optional<Shipment> shipmentOption = repository.findById(id);
		
		if (shipmentOption.isEmpty()) {
			throw new NoShipmentFoundException("Không tìm thấy thông tin vận chuyển cần xóa");
		}
		
		Shipment shipment = shipmentOption.get();
		
		shipment.setStatus(ShipmentStatus.INACTIVE);
		shipment.setDefault(false);
		shipment.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		
		repository.save(shipment);
		
		Optional<Shipment> newDefaultShipmentOptional = repository.findByCustomerAndIdNot(customer, shipment.getId());
		
//		If there is one another shipment
		if (newDefaultShipmentOptional.isPresent()) {
			Shipment newDefaultShipment = newDefaultShipmentOptional.get();
			newDefaultShipment.setDefault(true);
			newDefaultShipment.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
			repository.save(newDefaultShipment);
		}
		
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
