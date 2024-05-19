package com.inkmelo.order;

import java.sql.Date;
import java.util.List;

import com.inkmelo.orderdetail.OrderDetail;
import com.inkmelo.payment.Payment;
import com.inkmelo.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private float totalPrice;
	
	@Column(nullable = false)
	private String shipmentName;
	
	@Column(nullable = false)
	private String shipmentAddress;
	
	@Column(
			updatable = false,
			nullable = false
	)
	private Date createdAt;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
}
