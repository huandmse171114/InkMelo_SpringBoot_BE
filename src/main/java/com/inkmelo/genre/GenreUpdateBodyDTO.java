package com.inkmelo.genre;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GenreUpdateBodyDTO(
			@NotNull(message = "Mã số của thể loại sách không được để rỗng.")
			Integer id,
			@NotEmpty(message = "Tên của thể loại sách không được để rỗng.")
			String name,
			String description,
			GenreStatus status
		) {
}
