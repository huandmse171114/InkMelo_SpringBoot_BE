package com.inkmelo.book;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findAllByTitleContainingIgnoreCase(String titleKeyword);
	
	List<Book> findAllByAuthorContainingIgnoreCase(String authorKeyword);
	
	List<Book> findAllByStatus(BookStatus status);

}
