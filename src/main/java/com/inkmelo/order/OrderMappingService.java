package com.inkmelo.order;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkmelo.customer.CustomerMappingService;
import com.inkmelo.exception.NoShipmentFoundException;
import com.inkmelo.ghn.GHNApis;
import com.inkmelo.ghn.GHNTrackingOrderDTO;
import com.inkmelo.ghn.OrderTrackingStatus;
import com.inkmelo.orderdetail.OrderDetailMappingService;
import com.inkmelo.payment.PaymentMappingService;
import com.inkmelo.shipment.Shipment;
import com.inkmelo.shipment.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderMappingService {
	private final ShipmentRepository shipmentRepository;
	private final OrderDetailMappingService detailMapping;
	private final GHNApis ghnApis;
	private final OrderRepository orderRepository;
	private final CustomerMappingService customerMapping;
	private final PaymentMappingService paymentMapping;
	
	public Order orderCreateBodyDTOToOrder(OrderCreateBodyDTO orderDTO) {
		Optional<Shipment> shipmentOptional = shipmentRepository.findById(orderDTO.shipmentId());
		
		if (shipmentOptional.isEmpty()) throw new NoShipmentFoundException("Không tìm thấy thông tin giao hàng.");
		
		Shipment shipment = shipmentOptional.get();
		
		return Order.builder()
				.totalPrice(orderDTO.totalPrice())
				.orderPrice(orderDTO.totalPrice() - orderDTO.shippingFee())
				.shippingFee(orderDTO.shippingFee())
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
					
					order.setGhnOrderStatus(OrderTrackingStatus.fromValue(
									trackingOrderDTO.getData().getStatus()).getDescription());
					
					order.setPickupTime(trackingOrderDTO.getData().getPickup_time());
					
					orderRepository.save(order);
					
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
							.ghnOrderStatus(order.getGhnOrderStatus())
							.pickupTime(order.getPickupTime())
							.tag(trackingOrderDTO.getData().getTag())
							.build();
					
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}else {
				order.setGhnOrderStatus(OrderTrackingStatus.CANCEL.getDescription());
				orderRepository.save(order);
				
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
	
	public OrderAdminResponseDTO orderToOrderAdminResponseDTO(Order order) {
		System.out.println("======================Order no " + order.getGhnOrderCode() + " ==============================");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if (order.getGhnOrderCode() != null) {
			
			String trackingOrderDetail = ghnApis.trackOrder(order.getGhnOrderCode());
			if (!trackingOrderDetail.isEmpty()) {
				try {
					GHNTrackingOrderDTO trackingOrderDTO = objectMapper.readValue(trackingOrderDetail, GHNTrackingOrderDTO.class);
					
					order.setGhnOrderStatus(OrderTrackingStatus.fromValue(
									trackingOrderDTO.getData().getStatus()).getDescription());
					
					order.setPickupTime(trackingOrderDTO.getData().getPickup_time());
					order.setTag(trackingOrderDTO.getData().getTag());
					
					orderRepository.save(order);
					
					return OrderAdminResponseDTO.builder()
							.id(order.getId())
							.ghnOrderCode(order.getGhnOrderCode())
							.ghnServiceId(order.getGhnServiceId())
							.ghnOrderStatus(order.getGhnOrderStatus())
							.customer(customerMapping.customerToCustomerProfileResponseDTO(order.getCustomer()))
							.pickupTime(order.getPickupTime())
							.orderPrice(order.getOrderPrice())
							.shippingFee(order.getShippingFee())
							.totalPrice(order.getTotalPrice())
							.quantity(order.getQuantity())
							.expectedDeliveryTime(order.getExpectedDeliveryTime())
							.expectedDaysToDelivery(order.getExpectedDaysToDelivery())
							.deliveredDate(order.getDeliveredDate())
							.shipmentStreet(order.getShipmentStreet())
							.shipmentWard(order.getShipmentWard())
							.shipmentProvince(order.getShipmentProvince())
							.shipmentDistrictId(order.getShipmentDistrictId())
							.shipmentWardCode(order.getShipmentWardCode())
							.receiverName(order.getReceiverName())
							.contactNumber(order.getContactNumber())
							.contactNumber(order.getContactNumber())
							.tag(trackingOrderDTO.getData().getTag())
							.createdAt(order.getCreatedAt())
							.status(order.getStatus())
							.detail(order.getOrderDetails().stream()
									.map(detail -> detailMapping
											.orderDetailToOrderDetailResponseDTO(detail))
									.toList())
							.payment(paymentMapping.paymentToVNPayPaymentResponseDTO(order.getPayment()))
							.build();
					
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}else {
				order.setGhnOrderStatus(OrderTrackingStatus.CANCEL.getDescription());
				orderRepository.save(order);
				
				return OrderAdminResponseDTO.builder()
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
			return OrderAdminResponseDTO.builder()
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
