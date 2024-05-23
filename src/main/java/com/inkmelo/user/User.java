package com.inkmelo.user;

import java.sql.Date;
import java.util.List;

import com.inkmelo.bookrating.BookRating;
import com.inkmelo.cart.Cart;
import com.inkmelo.order.Order;
import com.inkmelo.payment.Payment;
import com.inkmelo.shipment.Shipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique = true,
			nullable = false,
			length = 100
	)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(length = 150)
	private String fullname;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private UserRole role;
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private UserStatus status;
	
}
