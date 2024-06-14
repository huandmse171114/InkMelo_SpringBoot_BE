package com.inkmelo.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryUpdateBodyDTO(
			@NotNull(message = "Mã số của danh mục không được để trống.")
			Integer id,
			@NotEmpty(message = "Tên của danh mục không được để trống.")
			String name,
			String description,
			CategoryStatus status
		) {

}
