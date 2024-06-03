package com.inkmelo.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryUpdateBodyDTO(
			@NotNull
			Integer id,
			@NotEmpty
			String name,
			String description,
			CategoryStatus status
		) {

}
