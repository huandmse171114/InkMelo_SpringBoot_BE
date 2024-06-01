package com.inkmelo.book;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.ISBN;
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
						.build())
				.collect(Collectors.toList());
	}
	
	public List<BookResponseDTO> searchBook(String keyword){
		return repository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword)
				.stream()
				.map(book -> BookResponseDTO.builder()
						.author(book.getAuthor())
						.title(book.getTitle())
						.build())
				.collect(Collectors.toList());

    }
	
}
