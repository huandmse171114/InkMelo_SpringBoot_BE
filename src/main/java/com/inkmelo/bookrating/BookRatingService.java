package com.inkmelo.bookrating;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookRatingService {
	private BookRatingRepository repository;

	public BookRatingService(BookRatingRepository repository) {
		this.repository = repository;
	}

	
	public BookRatingResponseDTO ratingToRatingResponseDTO(BookRating rating) {
		return BookRatingResponseDTO.builder()
				.star(rating.getStar())
				.comment(rating.getComment())
				.createdAt(rating.getCreatedAt())
				.build();
	}
	
	
}
