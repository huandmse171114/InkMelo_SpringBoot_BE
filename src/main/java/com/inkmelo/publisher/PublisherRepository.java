package com.inkmelo.publisher;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
	Publisher findByName(String publisherName);
	
	List<Publisher> findAllByStatus(PublisherStatus status);
	
	Page<Publisher> findAllByStatus(PublisherStatus status, Pageable pageable);

	List<Publisher> findAllByStatusAndNameContainingIgnoreCase(PublisherStatus status, String name);
	
	Page<Publisher> findAllByStatusAndNameContainingIgnoreCase(PublisherStatus status, String name, Pageable pageable);
	
	Page<Publisher> findAll(Pageable pageable);
	
	List<Publisher> findAllByNameContainingIgnoreCase(String name);
	
	Page<Publisher> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
