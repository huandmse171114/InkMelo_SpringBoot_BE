package com.inkmelo.order;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inkmelo.customer.Customer;
import com.inkmelo.orderdetail.OrderDetail;
import com.inkmelo.payment.Payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private float orderPrice;
	
	@Column(nullable = false)
	private float shippingFee;
	
	@Column(nullable = false)
	private float totalPrice;
	
	private Integer quantity;
	
	private Date expectedDeliveryTime;
	
	private Long expectedDaysToDelivery;
	
	private Date deliveredDate;
	
	@Column(nullable = false)
	private String shipmentStreet;
	
	@Column(nullable = false)
	private String shipmentWard;
	
	@Column(nullable = false)
	private String shipmentDistrict;
	
	@Column(nullable = false)
	private String shipmentProvince;
	
	@Column(nullable = false)
	private Integer shipmentDistrictId;
	
	@Column(nullable = false)
	private Integer shipmentProvinceId;
	
	@Column(nullable = false)
	private String shipmentWardCode;
	
	@Column(nullable = false)
	private String receiverName;
	
	@Column(nullable = false)
	private Integer ghnServiceId;
	
	private String ghbOrderCode;
	
	@Column(
			length = 12,
			nullable = false
	)
	private String contactNumber;
	
	@Column(
			updatable = false,
			nullable = false
	)
	private Date createdAt;
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order")
	@JsonManagedReference
	private List<OrderDetail> orderDetails;
	
	@ManyToOne
	@JoinColumn(
			name = "customer_id",
			nullable = false
	)
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
}
