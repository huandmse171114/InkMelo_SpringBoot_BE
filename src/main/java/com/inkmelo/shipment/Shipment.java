package com.inkmelo.shipment;

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
public class Shipment {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			nullable = false,
			length = 100
	)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	@Column(
			nullable = false,
			length = 12
	)
	private String phone;
	
	private String description;
	
	@Column(nullable = false)
	private boolean isActive;
	
	@Column(
			updatable = false,
			nullable = false
	)
	private Date createdAt;
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private ShipmentStatus status;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}
