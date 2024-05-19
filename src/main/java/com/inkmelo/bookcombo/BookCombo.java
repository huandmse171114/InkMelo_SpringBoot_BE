package com.inkmelo.bookcombo;

import java.sql.Date;
import java.util.List;

import com.inkmelo.resource.Resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class BookCombo {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
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
	
	@Column(nullable = false)
	private String lastChangedBy;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookComboStatus status;
	
	@ManyToMany
	@JoinTable(
			name = "bookcombo_resource",
			joinColumns = @JoinColumn(name = "bookcombo_id"),
			inverseJoinColumns = @JoinColumn(name = "resource_id")
	)
	private List<Resource> resources;
	
	
}
