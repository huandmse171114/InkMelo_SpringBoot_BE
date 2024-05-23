package com.inkmelo.cart;

import java.util.List;

import com.inkmelo.cartdetail.CartDetail;
import com.inkmelo.customer.Customer;
import com.inkmelo.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Cart {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne(mappedBy = "cart")
	private Customer customer;
	
	@OneToMany(mappedBy = "cart")
	private List<CartDetail> cartDetails;
	
}
