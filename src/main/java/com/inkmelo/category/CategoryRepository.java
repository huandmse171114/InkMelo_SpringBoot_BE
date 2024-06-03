package com.inkmelo.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findAllByStatus(CategoryStatus status);
}
