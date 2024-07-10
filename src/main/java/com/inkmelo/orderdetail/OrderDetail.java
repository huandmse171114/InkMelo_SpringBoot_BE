package com.inkmelo.orderdetail;

import com.inkmelo.book.Book;
import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.order.Order;

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
	
	@Column(nullable = false)
	private float totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "bookpackage_id")
	private BookPackage bookPackage;
	
	@ManyToOne
	@JoinColumn(
			name = "order_id",
			nullable = false
	)
	private Order order;
	
}
