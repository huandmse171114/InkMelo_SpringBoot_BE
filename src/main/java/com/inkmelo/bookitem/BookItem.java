package com.inkmelo.bookitem;

import java.sql.Date;
import java.util.List;

import com.inkmelo.book.Book;
import com.inkmelo.bookpackage.BookPackage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class BookItem {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			nullable = false,
			length = 50
	)
	private BookItemType type;
	
	private String source;
	
	@Column(
			updatable = false,
			nullable = false
	)
	private Date createdAt;
	
	private int duration;
	
	@Column(nullable = false)
	private int stock;
	
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
	private BookItemStatus status;
	
	@ManyToMany(mappedBy = "items")
	private List<BookPackage> bookPackages;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@Override
	public String toString() {
		return this.type + "-" + this.id;
	}
}
