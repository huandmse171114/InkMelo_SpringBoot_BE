package com.inkmelo.payment;

import java.sql.Date;

import com.inkmelo.customer.Customer;

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
public class Payment {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			nullable = false,
			length = 100
	)
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Column(
			nullable = false,
			updatable = false
	)
	private Date createdDate;
	
	@Column(nullable = false)
	private boolean isActive;
	
	private String thirdPartyCode;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}
