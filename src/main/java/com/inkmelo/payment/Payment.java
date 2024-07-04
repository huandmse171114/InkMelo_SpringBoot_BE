package com.inkmelo.payment;

import java.sql.Timestamp;

import com.inkmelo.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
	private Timestamp createdDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;
	
	@OneToOne(mappedBy = "payment")
	private Order order;
}
