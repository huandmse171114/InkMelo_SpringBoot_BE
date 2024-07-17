package com.inkmelo.order;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkmelo.exception.NoShipmentFoundException;
import com.inkmelo.ghn.GHNApis;
import com.inkmelo.ghn.GHNTrackingOrderDTO;
import com.inkmelo.ghn.OrderTrackingStatus;
import com.inkmelo.orderdetail.OrderDetailMappingService;
import com.inkmelo.shipment.Shipment;
import com.inkmelo.shipment.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderMappingService {
	private final ShipmentRepository shipmentRepository;
	private final OrderDetailMappingService detailMapping;
	private final GHNApis ghnApis;
	
	public Order orderCreateBodyDTOToOrder(OrderCreateBodyDTO orderDTO) {
		Optional<Shipment> shipmentOptional = shipmentRepository.findById(orderDTO.shipmentId());
		
		if (shipmentOptional.isEmpty()) throw new NoShipmentFoundException("Không tìm thấy thông tin giao hàng.");
		
		Shipment shipment = shipmentOptional.get();
		
		return Order.builder()
				.orderPrice(orderDTO.totalPrice())
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
		System.out.println("======================Order no " + order.getGhnOrderCode() + " ==============================");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if (order.getGhnOrderCode() != null) {
			
			String trackingOrderDetail = ghnApis.trackOrder(order.getGhnOrderCode());
			if (!trackingOrderDetail.isEmpty()) {
				try {
					GHNTrackingOrderDTO trackingOrderDTO = objectMapper.readValue(trackingOrderDetail, GHNTrackingOrderDTO.class);
					
					return OrderResponseDTO.builder()
							.id(order.getId())
							.ghnOrderCode(order.getGhnOrderCode())
							.orderPrice(order.getOrderPrice())
							.shippingFee(order.getShippingFee())
							.totalPrice(order.getTotalPrice())
							.detail(order.getOrderDetails().stream()
									.map(detail -> detailMapping
											.orderDetailToOrderDetailResponseDTO(detail))
									.toList())
							.createdAt(order.getCreatedAt())
							.ghnOrderStatus(OrderTrackingStatus.fromValue(
									trackingOrderDTO.getData().getStatus()).getDescription())
							.pickupTime(trackingOrderDTO.getData().getPickup_time())
							.tag(trackingOrderDTO.getData().getTag())
							.build();
					
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}else {
				return OrderResponseDTO.builder()
						.id(order.getId())
						.ghnOrderCode(order.getGhnOrderCode())
						.orderPrice(order.getOrderPrice())
						.shippingFee(order.getShippingFee())
						.totalPrice(order.getTotalPrice())
						.detail(order.getOrderDetails().stream()
								.map(detail -> detailMapping
										.orderDetailToOrderDetailResponseDTO(detail))
								.toList())
						.createdAt(order.getCreatedAt())
						.ghnOrderStatus(OrderTrackingStatus.CANCEL.getDescription())
						.build();
			}
		} else {
			return OrderResponseDTO.builder()
					.id(order.getId())
					.ghnOrderCode(order.getGhnOrderCode())
					.orderPrice(order.getOrderPrice())
					.shippingFee(order.getShippingFee())
					.totalPrice(order.getTotalPrice())
					.detail(order.getOrderDetails().stream()
							.map(detail -> detailMapping
									.orderDetailToOrderDetailResponseDTO(detail))
							.toList())
					.createdAt(order.getCreatedAt())
					.build();
		}
		
	}
}
