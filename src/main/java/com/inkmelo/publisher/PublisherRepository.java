package com.inkmelo.publisher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
	Publisher findByName(String publisherName);
	List<Publisher> findAllByStatus(PublisherStatus status);
	
}
