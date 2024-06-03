package com.inkmelo.publisher;

import lombok.Builder;

@Builder
public record PublisherResponseDTO(
			Integer id,
			String name,
			String description,
			String logoImg
		) {

}
