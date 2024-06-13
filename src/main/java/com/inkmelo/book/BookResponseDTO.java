package com.inkmelo.book;

import java.util.List;

import com.inkmelo.bookrating.BookRatingResponseDTO;
import com.inkmelo.genre.GenreResponseDTO;
import com.inkmelo.publisher.PublisherResponseDTO;

import lombok.Builder;

@Builder
public record BookResponseDTO(
			Integer id,
			String title,
			String ISBN,
			String publicationDecisionNumber,
			String publicationRegistConfirmNum,
			String depositCopy,
			String author,
			String description,
			String bookCoverImg,
			float averageStar,
			int totalRating,
			PublisherResponseDTO publisher,
			List<BookRatingResponseDTO> ratings,
			List<GenreResponseDTO> genres,
			BookStatus status
		) {

}
