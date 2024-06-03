package com.inkmelo.publisher;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record PublisherCreateBodyDTO(
			@NotEmpty
			String name,
			String description,
			String logoImg
		) {
}
