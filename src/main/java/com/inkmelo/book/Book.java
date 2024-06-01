package com.inkmelo.book;

import java.sql.Date;
import java.util.List;

import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.bookrating.BookRating;
import com.inkmelo.category.Category;
import com.inkmelo.genre.Genre;
import com.inkmelo.publisher.Publish;

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
	@JoinColumn(name = "publish_id")
	private Publish publish;
	
	@ManyToMany
	@JoinTable(
			name = "book_genre",
			joinColumns = @JoinColumn(name = "book_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false)
	)
	private List<Genre> genres;
	
	
	
}
