package com.inkmelo.order;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.inkmelo.customer.CustomerProfileResponseDTO;
import com.inkmelo.orderdetail.OrderDetail;
import com.inkmelo.orderdetail.OrderDetailResponseDTO;
import com.inkmelo.payment.VNPayPaymentResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAdminResponseDTO {
	private Integer id;
	private String ghnOrderCode;
	private Integer ghnServiceId;
	private String ghnOrderStatus;
	private CustomerProfileResponseDTO customer;
	private Timestamp pickupTime;
	private float orderPrice;
	private float shippingFee;
	private float totalPrice;
	private Integer quantity;
	private Date expectedDeliveryTime;
	private Long expectedDaysToDelivery;
	private Date deliveredDate;
	private String shipmentStreet;
	private String shipmentWard;
	private String shipmentDistrict;
	private String shipmentProvince;
	private Integer shipmentDistrictId;
	private Integer shipmentProvinceId;
	private String shipmentWardCode;
	private String receiverName;
	private String contactNumber;
	private List<String> tag;
	private Date createdAt;
	private OrderStatus status;
	private List<OrderDetailResponseDTO> detail;
	private VNPayPaymentResponseDTO payment;
}
