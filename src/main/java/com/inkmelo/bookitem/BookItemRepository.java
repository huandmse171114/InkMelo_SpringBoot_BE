package com.inkmelo.bookitem;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inkmelo.book.Book;


public interface BookItemRepository extends JpaRepository<BookItem, Integer> {

	List<BookItem> findAllByStatusOrderByIdAsc(BookItemStatus status);
	
	List<BookItem> findAllByStatusAndTypeAndBookInOrderByIdAsc(BookItemStatus status, BookItemType type, Collection<Book> books);
	
	Page<BookItem> findAllByStatusAndTypeAndBookInOrderByIdAsc(BookItemStatus status, BookItemType type, Collection<Book> books, Pageable pageable);
	
	List<BookItem> findAllByStatusAndBookInOrderByIdAsc(BookItemStatus status, Collection<Book> books);
	
	Page<BookItem> findAllByStatusAndBookInOrderByIdAsc(BookItemStatus status, Collection<Book> books, Pageable pageable);
	
	List<BookItem> findAllByBookInOrderByIdAsc(Collection<Book> books);
	
	Page<BookItem> findAllByBookInOrderByIdAsc(Collection<Book> books, Pageable pageable);
	
	List<BookItem> findAllByTypeAndBookInOrderByIdAsc(BookItemType type, Collection<Book> books);
	
	Page<BookItem> findAllByTypeAndBookInOrderByIdAsc(BookItemType type, Collection<Book> books, Pageable pageable);
	
	Optional<BookItem> findByBookAndTypeOrderByIdAsc(Book book, BookItemType type);
	
	List<BookItem> findAllByIdInOrderByIdAsc(Collection<Integer> id);
	
	
	
	
}
