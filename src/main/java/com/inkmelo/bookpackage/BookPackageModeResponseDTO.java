package com.inkmelo.bookpackage;

import lombok.Builder;

@Builder
public record BookPackageModeResponseDTO(
			Integer id,
			String description
		) {

}
