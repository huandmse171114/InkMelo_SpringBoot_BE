package com.inkmelo.publisher;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record PublisherCreateBodyDTO(
			@NotEmpty(message = "Tên của nhà xuất bản không được để trống.")
			String name,
			String description,
			String logoImg
		) {
}
