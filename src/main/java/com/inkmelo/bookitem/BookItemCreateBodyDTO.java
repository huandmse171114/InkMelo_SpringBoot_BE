package com.inkmelo.bookitem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookItemCreateBodyDTO(
			@NotNull(message = "Mã cuốn sách không được để trống.")
			Integer bookId,
			String source,
			Integer duration,
			Integer stock,
			BookItemType type
		) {

}
