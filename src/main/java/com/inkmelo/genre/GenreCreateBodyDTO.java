package com.inkmelo.genre;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record GenreCreateBodyDTO(
			@NotEmpty(message = "Tên của thể loại sách không được để trống.")
			String name,
			String description
		) {

}
