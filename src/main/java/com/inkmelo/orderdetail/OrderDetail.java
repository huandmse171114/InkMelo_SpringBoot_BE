package com.inkmelo.orderdetail;

import com.inkmelo.bookcombo.BookCombo;
import com.inkmelo.order.Order;
import com.inkmelo.resource.Resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private float itemPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private OrderDetailType type;
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private OrderDetailStatus status;
	
	@ManyToOne
	@JoinColumn(name = "resource_id")
	private Resource resource;
	
	@ManyToOne
	@JoinColumn(name = "bookcombo_id")
	private BookCombo bookCombo;
	
	@ManyToOne
	@JoinColumn(
			name = "order_id",
			nullable = false
	)
	private Order order;
	
}
