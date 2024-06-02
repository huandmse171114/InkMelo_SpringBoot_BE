package com.inkmelo.publisher;

import lombok.Builder;

@Builder
public record PublisherResponseDTO(
			String name,
			String description,
			String logoImg
		) {

}
