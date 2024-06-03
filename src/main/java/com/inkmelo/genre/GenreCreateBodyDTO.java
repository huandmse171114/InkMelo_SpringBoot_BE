package com.inkmelo.genre;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record GenreCreateBodyDTO(
			@NotEmpty
			String name,
			String description
		) {

}
