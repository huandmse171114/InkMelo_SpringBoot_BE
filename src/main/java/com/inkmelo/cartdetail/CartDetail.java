package com.inkmelo.cartdetail;

import java.util.List;

import com.inkmelo.bookcombo.BookCombo;
import com.inkmelo.cart.Cart;
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
public class CartDetail {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private int quantity;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CartDetailType type;
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "resource_id")
	private Resource resource;
	
	@ManyToOne
	@JoinColumn(name = "bookcombo_id")
	private BookCombo bookCombo;
	
}
