package com.inkmelo.book;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class BookService {

	private BookRepository repository;

	public BookService(BookRepository repository) {
		this.repository = repository;
	}

	public List<BookResponseDTO> findAllBooks() {
		return repository.findAll()
				.stream()
				.map(book -> BookResponseDTO.builder()
						.author(book.getAuthor())
						.ISBN(book.getISBN())
						.build())
				.collect(Collectors.toList());
	}
	
	
	
}
