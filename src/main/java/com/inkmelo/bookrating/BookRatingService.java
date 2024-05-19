package com.inkmelo.bookrating;

import org.springframework.stereotype.Service;

@Service
public class BookRatingService {
	private BookRatingRepository repository;

	public BookRatingService(BookRatingRepository repository) {
		this.repository = repository;
	}
	
	
}
