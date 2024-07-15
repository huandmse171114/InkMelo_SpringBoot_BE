package com.inkmelo.bookpackage;

import java.util.List;

import com.inkmelo.book.BookOrderResponseDTO;
import com.inkmelo.bookitem.BookItemOrderResponseDTO;
import com.inkmelo.category.CategoryResponseDTO;

import lombok.Builder;

@Builder
public record BookPackageOrderResponseDTO(
		Integer id,
		String title,
		String description,
		float price,
		int modeId,
		int stock,
		BookOrderResponseDTO book,
		List<BookItemOrderResponseDTO> items,
		CategoryResponseDTO category
		) {

}
