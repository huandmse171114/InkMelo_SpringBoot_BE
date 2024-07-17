package com.inkmelo.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findAllByStatusOrderByIdAsc(CategoryStatus status);
	
	List<Category> findAllByNameContainingIgnoreCaseOrderByIdAsc(String name);
	
	List<Category> findAllByStatusAndNameContainingIgnoreCaseOrderByIdAsc(CategoryStatus status, String name);
	
	Page<Category> findAllByOrderByIdAsc(Pageable pageable);
	
	Page<Category> findAllByNameContainingIgnoreCaseOrderByIdAsc(String name, Pageable pageable);
	
	Page<Category> findAllByStatusOrderByIdAsc(CategoryStatus status, Pageable pageable);
	
	Page<Category> findAllByStatusAndNameContainingIgnoreCaseOrderByIdAsc(CategoryStatus status, String name, Pageable pageable);
}
