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
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private String fullname;
	
	private Date dateOfBirth;
	
	private String phone;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String profileImg;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserGender gender;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserStatus status;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	@OneToMany(mappedBy = "user")
	private List<Payment> payments;
	
	@OneToMany(mappedBy = "user")
	private List<Shipment> shipments;
	
	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@OneToMany(mappedBy = "user")
	private List<BookRating> ratings;
	
	
	
}
