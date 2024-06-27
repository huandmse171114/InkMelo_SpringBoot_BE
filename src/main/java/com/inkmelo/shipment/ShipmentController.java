package com.inkmelo.shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.DefaultShipmentUpdateException;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoShipmentExistException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Shipment", description = "Shipment Management APIs")
public class ShipmentController {
	private final ShipmentService service;
	
	@Operation(summary = "Get Active Shipment Only",
			description = "This endpoint will return shipment that have ACTIVE status in DB with paging option and search by username | (Authority) CUSTOMER.")
	@GetMapping("/store/api/v1/customer/{username}/shipments")
	public ResponseEntity<?> getAllActiveShipment(
				@PathVariable("username") String username,
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size
			) {
		return service.findAllShipmentByStatus(ShipmentStatus.ACTIVE, username, page, size);
	}
	
	
	@Operation(summary = "Create new Shipment",
			description = "This endpoint will create new shipment with given information for corresponding customer base on username | (Authority) CUSTOMER.")
	@PostMapping("/store/api/v1/customer/{username}/shipments")
	public ResponseEntity<?> saveShipment(
				@PathVariable("username") String username,
				@Valid @RequestBody ShipmentCreateBodyDTO shipmentDTO
			) {
		service.saveShipment(username, shipmentDTO);
		
		return Utils.generateMessageResponseEntity("Tạo mới thông tin giao hàng thành công", HttpStatus.CREATED);
	}
	
	@PutMapping("/store/api/v1/customer/{username}/shipments")
	public ResponseEntity<?> updateShipment(
				@PathVariable("username") String username,
				@Valid @RequestBody ShipmentUpdateBodyDTO shipmentDTO
			) {
		service.updateShipment(username, shipmentDTO);
		
		return Utils.generateMessageResponseEntity("Cập nhật thông tin giao hàng thành công", HttpStatus.OK);
	}
	
	@DeleteMapping("/store/api/v1/customer/{username}/shipments/{id}")
	public ResponseEntity<?> deleteShipment(
				@PathVariable("username") String username,
				@PathVariable("id") Integer id
			) {
		service.deleteShipment(username, id);
		
		return Utils.generateMessageResponseEntity("Xóa thông tin giao hàng thành công", HttpStatus.OK);
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
	
	@ExceptionHandler(NoShipmentExistException.class)
	public ResponseEntity<?> handleNoShipmentExistException(
			NoShipmentExistException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DefaultShipmentUpdateException.class)
	public ResponseEntity<?> handleDefaultShipmentUpdateException(
			DefaultShipmentUpdateException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
}
