package com.inkmelo.genre;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GenreUpdateBodyDTO(
			@NotNull
			Integer id,
			@NotEmpty
			String name,
			String description,
			GenreStatus status
		) {
}
