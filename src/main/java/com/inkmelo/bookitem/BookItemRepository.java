package com.inkmelo.bookitem;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inkmelo.book.Book;


public interface BookItemRepository extends JpaRepository<BookItem, Integer> {

	List<BookItem> findAllByStatus(BookItemStatus status);
	
	List<BookItem> findAllByStatusAndTypeAndBookIn(BookItemStatus status, BookItemType type, Collection<Book> books);
	
	Page<BookItem> findAllByStatusAndTypeAndBookIn(BookItemStatus status, BookItemType type, Collection<Book> books, Pageable pageable);
	
	List<BookItem> findAllByStatusAndBookIn(BookItemStatus status, Collection<Book> books);
	
	Page<BookItem> findAllByStatusAndBookIn(BookItemStatus status, Collection<Book> books, Pageable pageable);
	
	List<BookItem> findAllByBookIn(Collection<Book> books);
	
	Page<BookItem> findAllByBookIn(Collection<Book> books, Pageable pageable);
	
	List<BookItem> findAllByTypeAndBookIn(BookItemType type, Collection<Book> books);
	
	Page<BookItem> findAllByTypeAndBookIn(BookItemType type, Collection<Book> books, Pageable pageable);
	
	Optional<BookItem> findByBookAndType(Book book, BookItemType type);
	
	List<BookItem> findAllByIdIn(Collection<Integer> id);
	
	
	
	
}
