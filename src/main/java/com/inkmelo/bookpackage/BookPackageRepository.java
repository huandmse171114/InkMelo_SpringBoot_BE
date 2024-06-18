package com.inkmelo.bookpackage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inkmelo.bookitem.BookItem;
import com.inkmelo.book.Book;



public interface BookPackageRepository extends JpaRepository<BookPackage, Integer> {
	
	List<BookPackage> findAllByStatus(BookPackageStatus status);
	
	Optional<BookPackage> findByBookAndItems(Book book, Collection<BookItem> items);
	

}
