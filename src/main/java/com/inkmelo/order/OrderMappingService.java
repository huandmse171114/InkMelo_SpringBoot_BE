package com.inkmelo.order;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderMappingService {
	
	public Order orderCreateBodyDTOToOrder(OrderCreateBodyDTO orderDTO) {
		return Order.builder()
				.orderPrice(orderDTO.totalPrice())
				.receiverName(orderDTO.receiverName())
				.contactNumber(orderDTO.contactNumber())
				.shipmentStreet(orderDTO.shipmentStreet())
				.shipmentWard(orderDTO.shipmentWard())
				.shipmentDistrict(orderDTO.shipmentDistrict())
				.shipmentProvince(orderDTO.shipmentProvince())
				.shipmentDistrictId(orderDTO.districtId())
				.shipmentWardCode(orderDTO.wardCode())
				.shipmentProvinceId(orderDTO.provinceId())
				.createdAt(Date.valueOf(LocalDate.now()))
				.status(OrderStatus.PAYMENT_PENDING)
				.build();
	}
}
