package com.inkmelo.bookitem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookItemCreateBodyDTO(
			@NotNull(message = "Mã cuốn sách không được để trống.")
			Integer bookId,
			@NotEmpty(message = "Dữ liệu của tài nguyên sách không được để trống.")
			String source,
			int duration,
			@NotEmpty(message = "Phân loại tài nguyên sách không được để trống.")
			BookItemType type
		) {

}
