package com.inkmelo.bookpackage;

import java.sql.Date;
import java.util.List;

import com.inkmelo.book.BookResponseDTO;
import com.inkmelo.bookitem.BookItemResponseDTO;
import com.inkmelo.category.CategoryResponseDTO;

import lombok.Builder;

@Builder
public record BookPackageAdminResponseDTO(
			Integer id,
			String title,
			String description,
			float price,
			int modeId,
			int stock,
			BookResponseDTO book,
			List<BookItemResponseDTO> items,
			CategoryResponseDTO category,
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy,
			BookPackageStatus status
		) {

}
