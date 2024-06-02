package com.inkmelo.book;

import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class BookService {

	private final BookRepository repository;
	private final BookMappingService mappingService;
	
	
	
	public BookService(BookRepository repository, BookMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public List<BookResponseDTO> findAllBookByStatus(BookStatus status) {
		return repository.findAllByStatus(status)
				.stream()
				.map(book -> mappingService.bookToBookResponseDTO(book))
				.toList();
	}
	
	public List<BookAdminResponseDTO> findAllBook() {
		return repository.findAll()
				.stream()
				.map(book -> mappingService.bookToBookAdminResponseDTO(book))
				.toList();
	}
	
	public List<BookResponseDTO> searchBook(String keyword){
		return repository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword)
				.stream()
				.map(book -> mappingService.bookToBookResponseDTO(book))
				.toList();

    }
	
}
