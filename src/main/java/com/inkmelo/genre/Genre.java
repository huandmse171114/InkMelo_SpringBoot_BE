package com.inkmelo.genre;

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
public class Genre {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			nullable = false,
			unique = true,
			length = 100
	)
	private String name;
	
	private String description;
	
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
	private GenreStatus status;
	
	@ManyToMany(mappedBy = "genres")
	private List<Book> books;
	
}
