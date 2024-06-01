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
public class Publish {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			nullable = false,
			length = 100
	)
	private String publisherName;
	
	@Column(
			length = 17, // ISBN13 format,
			unique = true,
			nullable = false
	)
	private String ISBN;
	
	@Column(length = 150)
	private String publicationDecisionNumber;
	
	@Column(length = 150)
	private String publicationRegistConfirmNum;
	
	@Column(length = 150)
	private String depositCopy;
	
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
	private PublishStatus status;
	
	@OneToMany(mappedBy = "publish")
	private List<Book> books;
}
