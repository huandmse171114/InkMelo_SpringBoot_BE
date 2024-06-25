package com.inkmelo.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CategoryCreateBodyDTO(
			@NotEmpty(message = "Tên danh mục không được để trống.")
			String name,
			String description
		) {

}
