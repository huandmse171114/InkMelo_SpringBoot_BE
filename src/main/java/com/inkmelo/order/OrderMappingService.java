package com.inkmelo.order;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoShipmentFoundException;
import com.inkmelo.orderdetail.OrderDetailMappingService;
import com.inkmelo.shipment.Shipment;
import com.inkmelo.shipment.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderMappingService {
	private final ShipmentRepository shipmentRepository;
	private final OrderDetailMappingService detailMapping;
	
	public Order orderCreateBodyDTOToOrder(OrderCreateBodyDTO orderDTO) {
		Optional<Shipment> shipmentOptional = shipmentRepository.findById(orderDTO.shipmentId());
		
		if (shipmentOptional.isEmpty()) throw new NoShipmentFoundException("Không tìm thấy thông tin giao hàng.");
		
		Shipment shipment = shipmentOptional.get();
		
		return Order.builder()
				.orderPrice(orderDTO.totalPrice())
				.shippingFee(orderDTO.shippingFee())
				.totalPrice(orderDTO.totalPrice() + orderDTO.shippingFee())
				.quantity(orderDTO.quantity())
				.receiverName(shipment.getReceiverName())
				.contactNumber(shipment.getContactNumber())
				.shipmentStreet(shipment.getStreet())
				.shipmentWard(shipment.getWard())
				.shipmentDistrict(shipment.getDistrict())
				.shipmentProvince(shipment.getProvince())
				.shipmentDistrictId(shipment.getDistrictId())
				.shipmentWardCode(shipment.getWardCode())
				.shipmentProvinceId(shipment.getProvinceId())
				.ghnServiceId(orderDTO.serviceId())
				.createdAt(Date.valueOf(LocalDate.now()))
				.status(OrderStatus.PAYMENT_PENDING)
				.build();
	}
	
	public OrderResponseDTO orderToOrderResponseDTO(Order order) {
		
		
		
		return OrderResponseDTO.builder()
				.id(order.getId())
				.ghnOrderCode(order.getGhbOrderCode())
				.orderPrice(order.getOrderPrice())
				.shippingFee(order.getShippingFee())
				.totalPrice(order.getTotalPrice())
				.detail(order.getOrderDetails().stream()
						.map(detail -> detailMapping
								.orderDetailToOrderDetailResponseDTO(detail))
						.toList())
				.build();
	}
}
