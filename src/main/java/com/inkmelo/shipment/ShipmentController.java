package com.inkmelo.shipment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
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
	
	@PostMapping("/store/api/v1/customer/{username}/shipments")
	public ResponseEntity<?> saveShipment(
				@PathVariable("username") String username,
				@Valid @RequestBody ShipmentCreateBodyDTO shipmentDTO
			) {
		service.saveShipment(username, shipmentDTO);
		
		return Utils.generateMessageResponseEntity("Tạo mới thông tin giao hàng thành công", HttpStatus.CREATED);
	}
}
