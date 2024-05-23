package com.inkmelo.publisher;

import java.sql.Date;
import java.util.List;

import com.inkmelo.book.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Publisher {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			nullable = false,
			unique = true,
			length = 100
	)
	private String name;
	
	private String address;
	
	@Column(length = 100)
	private String email;
	
	@Column(length = 12)
	private String phone;
	
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
	private PublisherStatus status;
	
	@OneToMany(mappedBy = "publisher")
	private List<Book> books;
}
