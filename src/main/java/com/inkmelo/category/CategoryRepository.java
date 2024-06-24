package com.inkmelo.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findAllByStatus(CategoryStatus status);
	
	List<Category> findAllByNameContainingIgnoreCase(String name);
	
	List<Category> findAllByStatusAndNameContainingIgnoreCase(CategoryStatus status, String name);
	
	Page<Category> findAll(Pageable pageable);
	
	Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
	
	Page<Category> findAllByStatus(CategoryStatus status, Pageable pageable);
	
	Page<Category> findAllByStatusAndNameContainingIgnoreCase(CategoryStatus status, String name, Pageable pageable);
}
