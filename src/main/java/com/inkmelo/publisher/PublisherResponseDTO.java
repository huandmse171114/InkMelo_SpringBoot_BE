package com.inkmelo.publisher;

import lombok.Builder;

@Builder
public record PublisherResponseDTO(
			int id,
			String name,
			String description,
			String logoImg
		) {

}
