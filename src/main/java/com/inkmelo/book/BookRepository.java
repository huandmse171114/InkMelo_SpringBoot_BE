package com.inkmelo.book;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.inkmelo.genre.Genre;


public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findAllByTitleContainingIgnoreCaseOrderByIdAsc(String titleKeyword);
	
	List<Book> findAllByTitleContainingIgnoreCaseAndGenresOrderByIdAsc(String title, List<Genre> genres);
	
	List<Book> findAllByAuthorContainingIgnoreCaseOrderByIdAsc(String authorKeyword);
	
	List<Book> findAllByAuthorContainingIgnoreCaseAndGenresOrderByIdAsc(String author, List<Genre> genres);
	
	List<Book> findAllByStatusOrderByIdAsc(BookStatus status);
	
	List<Book> findAllByStatusAndTitleContainingIgnoreCaseOrderByIdAsc(BookStatus status, String name);
	
	List<Book> findAllByStatusAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdAsc(BookStatus status, String title, String author);
	
	Page<Book> findAllByStatusAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdAsc(BookStatus status, String title, String author, Pageable pageable);
	
	List<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdAsc(String title, String author);
	
	Page<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdAsc(String title, String author, Pageable pageable);
	
	Page<Book> findAllByStatusOrderByIdAsc(BookStatus status, Pageable pageable);
	
	List<Book> findAllByGenresOrderByIdAsc(List<Genre> genres);
	
	List<Book> findAllByTitleContainingIgnoreCase(String title);

}
