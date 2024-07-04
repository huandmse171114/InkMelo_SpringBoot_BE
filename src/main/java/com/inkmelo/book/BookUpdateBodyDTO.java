package com.inkmelo.book;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookUpdateBodyDTO(
			@NotNull(message = "Mã số cuốn sách không được để trống.")
			Integer id,
			@NotEmpty(message = "Tiêu đề cuốn sách không được để trống.")
			String title,
			@NotEmpty(message = "Mã ISBN không được để trống.")
			String ISBN,
			String publicationDecisionNumber,
			String publicationRegistConfirmNum,
			String depositCopy,
			@NotEmpty(message = "Tác giả cuốn sách không được để trống.")
			String author,
			String description,
			@NotEmpty(message = "Ảnh bìa cuốn sách không được để trống.")
			String bookCoverImg,
			@NotEmpty(message = "Cuốn sách phải được phân vào ít nhất một thể loại sách.")
			List<Integer> genreIds,
			@NotNull(message = "Nhà xuất bản sách không được để trống.")
			Integer publisherId,
			BookStatus status

			
		) {

}
