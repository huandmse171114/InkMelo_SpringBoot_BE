package com.inkmelo.book;

import java.sql.Date;
import java.util.List;

import com.inkmelo.bookrating.BookRatingResponseDTO;
import com.inkmelo.genre.GenreResponseDTO;
import com.inkmelo.publisher.PublisherResponseDTO;

import lombok.Builder;

@Builder
public record BookAdminResponseDTO(
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
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy,
			BookStatus status
		) {
	
}
