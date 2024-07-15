package com.inkmelo.bookitem;

import lombok.Builder;

@Builder
public record BookItemOrderResponseDTO(
			Integer id,
			String bookTitle,
			String bookCoverImg,
			BookItemType type
		) {

}
