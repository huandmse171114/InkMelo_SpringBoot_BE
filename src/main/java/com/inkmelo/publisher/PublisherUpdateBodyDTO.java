package com.inkmelo.publisher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PublisherUpdateBodyDTO(
			@NotNull(message = "Mã số của nhà xuất bản không được để trống.")
			Integer id,
			@NotEmpty(message = "Tên của nhà xuất bản không được để trống.")
			String name,
			String description,
			String logoImg,
			PublisherStatus status
		) {

}
