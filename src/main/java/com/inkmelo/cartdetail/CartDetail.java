package com.inkmelo.cartdetail;

import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.cart.Cart;

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
public class CartDetail {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(
			name = "bookpackage_id",
			nullable = false
	)
	private BookPackage bookPackage;
	
	@Enumerated(EnumType.STRING)
	@Column(
			length = 50,
			nullable = false
	)
	private CartDetailStatus status;
	
}
