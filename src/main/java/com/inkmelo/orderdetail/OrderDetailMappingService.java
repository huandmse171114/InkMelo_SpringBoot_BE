package com.inkmelo.orderdetail;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.bookpackage.BookPackageRepository;
import com.inkmelo.cartdetail.CartDetailResponseDTO;
import com.inkmelo.exception.NoBookPackageFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailMappingService {
	
	private final BookPackageRepository bookPackageRepository;
	
	public OrderDetail cartDetailResponseDTOToOrderDetail(CartDetailResponseDTO cartDetailDTO) {
		
		Optional<BookPackage> bookPackageOptional = bookPackageRepository.findById(cartDetailDTO.bookPackageId());
		
		if (bookPackageOptional.isEmpty()) {
			throw new NoBookPackageFoundException("Thanh toán thất bại: gói tài nguyên sách không tồn tại");
		}
		
		BookPackage bookPackage = bookPackageOptional.get();
		
		return OrderDetail.builder()
				.quantity(cartDetailDTO.quantity())
				.itemPrice(cartDetailDTO.bookPackagePrice())
				.bookPackage(bookPackage)
				.totalPrice(cartDetailDTO.quantity() * cartDetailDTO.bookPackagePrice())
				.build();
	}
}
