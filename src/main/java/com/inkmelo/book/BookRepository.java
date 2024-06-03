package com.inkmelo.book;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(String authorKeyword, String titleKeyword);
	List<Book> findAllByStatus(BookStatus status);

}
