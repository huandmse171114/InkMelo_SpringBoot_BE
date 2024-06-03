package com.inkmelo.genre;

import lombok.Builder;

@Builder
public record GenreResponseDTO(
			Integer id,
			String name,
			String description
		) {

}
