package com.inkmelo.customer;

import java.sql.Date;
import java.util.List;

import com.inkmelo.bookrating.BookRating;
import com.inkmelo.cart.Cart;
import com.inkmelo.order.Order;
import com.inkmelo.payment.Payment;
import com.inkmelo.shipment.Shipment;
import com.inkmelo.user.User;

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
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			length = 150, 
			nullable = false
	)
	private String fullname;
	
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private CustomerGender gender;
	
	@Column(length = 12)
	private String phone;
	
	@Column(
			unique = true,
			nullable = false,
			length = 100
	)
	private String email;
	
	private String profileImg;
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;
	
	@OneToMany(mappedBy = "customer")
	private List<Payment> payments;
	
	@OneToMany(mappedBy = "customer")
	private List<Shipment> shipments;
	
	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@OneToMany(mappedBy = "customer")
	private List<BookRating> ratings;
	
	@OneToOne
	@JoinColumn(
			name = "user_id",
			nullable = false
	)
	private User user;
	
	
}
