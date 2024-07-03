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
				.wardCode(shipmentDTO.wardCode())
				.districtId(shipmentDTO.districtId())
				.provinceId(shipmentDTO.provinceId())
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
				.id(shipment.getId())
				.receiverName(shipment.getReceiverName())
				.contactNumber(shipment.getContactNumber())
				.description(shipment.getDescription())
				.street(shipment.getStreet())
				.ward(shipment.getWard())
				.wardCode(shipment.getWardCode())
				.district(shipment.getDistrict())
				.districtId(shipment.getDistrictId())
				.province(shipment.getProvince())
				.provinceId(shipment.getProvinceId())
				.isDefault(shipment.isDefault())
				.build();
	}
}
