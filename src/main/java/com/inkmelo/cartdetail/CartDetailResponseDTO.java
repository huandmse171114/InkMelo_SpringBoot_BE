package com.inkmelo.cartdetail;

import java.sql.Date;

import lombok.Builder;

@Builder
public record CartDetailResponseDTO(
			Integer id,
			String bookTitle,
			String bookAuthor,
			String bookCoverImg,
			Integer bookPackageId,
			String bookPackageTitle,
			String bookPackageDescription,
			float bookPackagePrice,
			int bookPackageStock,
			int quantity,
			Date lastUpdatedTime
		) {

}
