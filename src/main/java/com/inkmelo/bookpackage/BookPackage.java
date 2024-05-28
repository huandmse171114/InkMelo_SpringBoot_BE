package com.inkmelo.bookpackage;

import java.sql.Date;
import java.util.List;

import com.inkmelo.book.Book;
import com.inkmelo.bookitem.BookItem;

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
public class BookPackage {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			length = 150,
			nullable = false
	)
	private String title;
	
	private String description;
	
	@Column(nullable = false)
	private float price;
	
	@Column(nullable = false)
	private int mode;
	
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
	
	@Enumerated(EnumType.STRING)
	@Column(
			nullable = false,
			length = 50
	)
	private BookPackageStatus status;
	
	@ManyToMany
	@JoinTable(
			name = "bookpackage_bookitem",
			joinColumns = @JoinColumn(name = "bookpackage_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "bookitem_id", nullable = false)
	)
	private List<BookItem> items;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
}
