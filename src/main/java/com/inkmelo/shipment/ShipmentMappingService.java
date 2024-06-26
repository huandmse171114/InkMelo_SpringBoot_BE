package com.inkmelo.shipment;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentMappingService {
	
	public Shipment shipmentCreateBodyDTOToShipment(ShipmentCreateBodyDTO shipmentDTO) {
		return Shipment.builder()
				.receiverName(shipmentDTO.receiverName())
				.street(shipmentDTO.street())
				.ward(shipmentDTO.ward())
				.district(shipmentDTO.district())
				.province(shipmentDTO.province())
				.contactNumber(shipmentDTO.contactNumber())
				.description(shipmentDTO.description())
				.isDefault(shipmentDTO.isDefault())
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.status(ShipmentStatus.ACTIVE)
				.build();
	}
	
	public ShipmentResponseDTO shipmentToShipmentResponseDTO(Shipment shipment) {
		return ShipmentResponseDTO.builder()
				.receiverName(shipment.getReceiverName())
				.contactNumber(shipment.getContactNumber())
				.description(shipment.getDescription())
				.street(shipment.getStreet())
				.ward(shipment.getWard())
				.district(shipment.getDistrict())
				.province(shipment.getProvince())
				.isDefault(shipment.isDefault())
				.build();
	}
}
