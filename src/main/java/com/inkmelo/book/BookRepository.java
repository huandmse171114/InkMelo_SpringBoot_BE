package com.inkmelo.book;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.inkmelo.genre.Genre;


public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findAllByTitleContainingIgnoreCase(String titleKeyword);
	
	List<Book> findAllByTitleContainingIgnoreCaseAndGenres(String title, List<Genre> genres);
	
	List<Book> findAllByAuthorContainingIgnoreCase(String authorKeyword);
	
	List<Book> findAllByAuthorContainingIgnoreCaseAndGenres(String author, List<Genre> genres);
	
	List<Book> findAllByStatus(BookStatus status);
	
	List<Book> findAllByStatusAndTitleContainingIgnoreCase(BookStatus status, String name);
	
	List<Book> findAllByStatusAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(BookStatus status, String title, String author);
	
	Page<Book> findAllByStatusAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(BookStatus status, String title, String author, Pageable pageable);
	
	List<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
	
	Page<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);
	
	Page<Book> findAllByStatus(BookStatus status, Pageable pageable);
	
	List<Book> findAllByGenres(List<Genre> genres);

}
