package com.inkmelo.category;

import lombok.Builder;

@Builder
public record CategoryResponseDTO(
			Integer id,
			String name,
			String description
		) {

}
