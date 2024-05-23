package com.inkmelo.bookrating;

import java.sql.Date;

import com.inkmelo.book.Book;
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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRating {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private int star;
	
	private String comment;
	
	@Column(
			updatable = false,
			nullable = false
	)
	private Date createdAt;
	
	@Column(nullable = false)
	private Date lastUpdatedTime;
	
	@Column(
			nullable = false,
			length = 100
			)
	private String lastChangedBy;

	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
			)
	private BookRatingStatus status;
	
	@ManyToOne
	@JoinColumn(
			name = "book_id",
			nullable = false
	)
	private Book book;
	
	@ManyToOne
	@JoinColumn(
			name = "customer_id",
			nullable = false
	)
	private Customer customer;
	
}
