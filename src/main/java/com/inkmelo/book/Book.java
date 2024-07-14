package com.inkmelo.book;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inkmelo.bookitem.BookItem;
import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.bookrating.BookRating;
import com.inkmelo.genre.Genre;
import com.inkmelo.publisher.Publisher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Book {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			length = 100,
			nullable = false
	)
	private String title;
	
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
			length = 100,
			nullable = false
	)
	private String lastChangedBy;
	
	@Column(length = 100)
	private String author;
	
	@Column(length = 2000)
	private String description;
	
	private String bookCoverImg;
	
	@Column(nullable = false)
	private float averageStar;
	
	@Column(nullable = false)
	private int totalRating;
	
	@Enumerated(EnumType.STRING)
	@Column(
			length = 50,
			nullable = false
	)
	private BookStatus status;
	
	@OneToMany(mappedBy = "book")
	private List<BookPackage> packages;
	
	@OneToMany(mappedBy = "book")
	private List<BookRating> ratings;
	
	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;
	
	@ManyToMany
	@JoinTable(
			name = "book_genre",
			joinColumns = @JoinColumn(name = "book_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false)
	)
	private List<Genre> genres;
	
	@OneToMany(mappedBy = "book")
	@JsonBackReference
	private List<BookItem> items;
	
}
