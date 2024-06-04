package com.inkmelo.publisher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PublisherUpdateBodyDTO(
			@NotNull
			Integer id,
			@NotEmpty
			String name,
			String description,
			String logoImg,
			PublisherStatus status
		) {

}
