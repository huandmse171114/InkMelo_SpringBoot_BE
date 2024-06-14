package com.inkmelo.bookitem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inkmelo.book.Book;


public interface BookItemRepository extends JpaRepository<BookItem, Integer> {

	List<BookItem> findAllByStatus(BookItemStatus status);
	
	Optional<BookItem> findByBookAndType(Book book, BookItemType type);
}
