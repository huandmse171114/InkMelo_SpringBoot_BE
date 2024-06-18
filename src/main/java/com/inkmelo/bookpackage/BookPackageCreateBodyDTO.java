package com.inkmelo.bookpackage;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookPackageCreateBodyDTO(
			@NotEmpty(message = "Tiêu đề của gói tài nguyên sách không được để trống.")
			String title,
			String description,
			@NotNull(message = "Giá của gói tài nguyên sách không được để trống.")
			Float price,
			@NotNull(message = "Chế độ của gói tài nguyên sách không được để trống.")
			Integer modeId,
			@NotNull(message = "Gói tài nguyên sách phải được liên kết với một cuốn sách nhất định.")
			Integer bookId,
			@NotEmpty(message = "Gói tài nguyên sách phải được thêm vào ít nhất một tài nguyên.")
			List<Integer> itemIds,
			@NotNull(message = "Gói tài nguyên sách phải được phân loại vào danh mục phù hợp.")
			Integer categoryId
		) {

}
