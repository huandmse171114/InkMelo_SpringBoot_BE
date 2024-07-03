package com.inkmelo.cartdetail;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.cart.Cart;

@Service
public class CartDetailMappingService {
	
	public CartDetailResponseDTO cartDetailToCartDetailResponseDTO(CartDetail cartDetail) {
		BookPackage bookPackage = cartDetail.getBookPackage();
		
		return CartDetailResponseDTO.builder()
				.id(cartDetail.getId())
				.bookTitle(bookPackage.getBook().getTitle())
				.bookAuthor(bookPackage.getBook().getAuthor())
				.bookCoverImg(bookPackage.getBook().getBookCoverImg())
				.bookPackageId(bookPackage.getId())
				.bookPackageTitle(bookPackage.getTitle())
				.bookPackageDescription(bookPackage.getDescription())
				.bookPackageStock(bookPackage.getStock())
				.bookPackagePrice(bookPackage.getPrice())
				.quantity(cartDetail.getQuantity())
				.lastUpdatedTime(cartDetail.getLastUpdatedTime())
				.build();
	}
	
	public CartDetail cartDetailCreateBodyDTOToCartDetail(Cart cart, BookPackage bookPackage, Integer quantity) {
		
		return CartDetail.builder()
				.quantity(quantity)
				.cart(cart)
				.bookPackage(bookPackage)
				.status(CartDetailStatus.ACTIVE)
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.build();
		
	}
} 
