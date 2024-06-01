package com.inkmelo.genre;

import lombok.Builder;

@Builder
public record GenreResponseDTO(
		String name,
		String description) {

}
