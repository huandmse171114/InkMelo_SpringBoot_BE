package com.inkmelo.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CategoryCreateBodyDTO(
			@NotEmpty
			String name,
			String description
		) {

}
