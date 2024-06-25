package com.inkmelo.bookitem;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.Book;
import com.inkmelo.book.BookRepository;
import com.inkmelo.exception.NoBookFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookItemMappingService {
	
	private final BookRepository bookRepository;
	
	public BookItemResponseDTO bookItemToBookItemResponseDTO(BookItem bookItem) {
		return BookItemResponseDTO.builder()
				.id(bookItem.getId())
				.bookId(bookItem.getBook().getId())
				.bookTitle(bookItem.getBook().getTitle())
				.bookCoverImg(bookItem.getBook().getBookCoverImg())
				.type(bookItem.getType())
				.source(bookItem.getSource())
				.duration(bookItem.getDuration())
				.stock(bookItem.getStock())
				.build();
	}

	public BookItemAdminResponseDTO bookItemToBookItemAdminResponseDTO(BookItem bookItem) {
		return BookItemAdminResponseDTO.builder()
				.id(bookItem.getId())
				.bookId(bookItem.getBook().getId())
				.bookTitle(bookItem.getBook().getTitle())
				.bookCoverImg(bookItem.getBook().getBookCoverImg())
				.type(bookItem.getType())
				.source(bookItem.getSource())
				.duration(bookItem.getDuration())
				.stock(bookItem.getStock())
				.createdAt(bookItem.getCreatedAt())
				.lastUpdatedTime(bookItem.getLastUpdatedTime())
				.lastChangedBy(bookItem.getLastChangedBy())
				.status(bookItem.getStatus())
				.build();
	}

	public BookItem bookItemCreateBodyDTOToBookItem(BookItemCreateBodyDTO bookItemDTO) 
		throws NoBookFoundException {
		
		Optional<Book> bookOption = bookRepository.findById(bookItemDTO.bookId());
		
		if (bookOption.isEmpty()) {
			throw new NoBookFoundException("Tạo mới tài nguyên sách thất bại. Cuốn sách không tồn tại.");
		}
		
		return BookItem.builder()
				.book(bookOption.get())
				.source(bookItemDTO.source())
				.type(bookItemDTO.type())
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.lastChangedBy(SecurityContextHolder.getContext()
						.getAuthentication().getName())
				.status(BookItemStatus.ACTIVE)
				.build();
	}
}
