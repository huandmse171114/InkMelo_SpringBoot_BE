package com.inkmelo.bookitem;

import java.sql.Date;

import lombok.Builder;

@Builder
public record BookItemAdminResponseDTO(
			Integer id,
			Integer bookId,
			String bookTitle,
			String bookCoverImg,
			BookItemType type,
			String source,
			int duration,
			Date createdAt,
			Date lastUpdatedTime,
			String lastChangedBy,
			BookItemStatus status
		) {

}
