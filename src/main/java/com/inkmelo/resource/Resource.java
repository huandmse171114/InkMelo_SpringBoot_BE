package com.inkmelo.resource;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inkmelo.book.Book;
import com.inkmelo.bookcombo.BookCombo;

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
public class Resource {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ResourceType type;
	
	private String source;
	
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
	private ResourceStatus status;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	@JsonBackReference
	private Book book;
	
	@ManyToMany(mappedBy = "resources")
	private List<BookCombo> bookCombos;
}
