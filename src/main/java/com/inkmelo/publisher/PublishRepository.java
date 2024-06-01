package com.inkmelo.publisher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishRepository extends JpaRepository<Publish, Integer>{
	Publish findByPublisherName(String publisherName);
	
	Publish findByISBN(String iSBN);
}
