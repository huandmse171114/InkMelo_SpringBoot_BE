package com.inkmelo.book;

import lombok.Builder;

@Builder
public record BookResponseDTO(
			String ISBN,
			String title,
			float price,
			String author,
			String description, 
			BookStatus status
		) {

}
