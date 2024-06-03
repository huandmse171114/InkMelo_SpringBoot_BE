package com.inkmelo.publisher;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record PublisherUpdateBodyDTO(
			@NotEmpty
			Integer id,
			@NotEmpty
			String name,
			String description,
			String logoImg
		) {

}
