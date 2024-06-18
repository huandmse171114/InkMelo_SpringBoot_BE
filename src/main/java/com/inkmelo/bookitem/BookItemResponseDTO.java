package com.inkmelo.bookitem;


import lombok.Builder;

@Builder
public record BookItemResponseDTO(
			Integer id,
			Integer bookId,
			String bookTitle,
			String bookCoverImg,
			BookItemType type,
			String source,
			int duration,
			int stock
		) {

}
