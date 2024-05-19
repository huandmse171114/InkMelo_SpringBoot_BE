package com.inkmelo.category;

import java.sql.Date;
import java.util.List;

import com.inkmelo.book.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Category {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
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
	private CategoryStatus status;
	
	@ManyToMany(mappedBy = "categories")
	private List<Book> books;
	
	
	
}
