package com.inkmelo.bookrating;

import java.sql.Date;

import com.inkmelo.book.Book;
import com.inkmelo.user.User;

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
	
	@Column(nullable = false)
	private String lastChangedBy;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookRatingStatus status;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}
