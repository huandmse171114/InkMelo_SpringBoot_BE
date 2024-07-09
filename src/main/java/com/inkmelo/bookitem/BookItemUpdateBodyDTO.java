package com.inkmelo.bookitem;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookItemUpdateBodyDTO(
			@NotNull(message = "Mã tài nguyên sách không được để trống.")
			Integer id,
			@NotNull(message = "Mã cuốn sách không được để trống.")
			Integer bookId,
			String source,
			Integer duration,
			Integer stock,
			BookItemType type,
			BookItemStatus status
		) {

}
