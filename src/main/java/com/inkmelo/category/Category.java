package com.inkmelo.category;

import java.sql.Date;
import java.util.List;

import com.inkmelo.bookitem.BookItem;
import com.inkmelo.bookpackage.BookPackage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Category {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(
			unique = true,
			nullable = false
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
			length = 100,
			nullable = false
	)
	private String lastChangedBy;
	
	@Enumerated(EnumType.STRING)
	@Column(
			length = 50,
			nullable = false
	)
	private CategoryStatus status;
	
	@ManyToMany(mappedBy = "categories")
	private List<BookPackage> bookPackages;
	
}
