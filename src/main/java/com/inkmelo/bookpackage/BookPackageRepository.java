package com.inkmelo.bookpackage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inkmelo.bookitem.BookItem;
import com.inkmelo.book.Book;
import com.inkmelo.category.Category;
import com.inkmelo.bookpackage.BookPackageStatus;





public interface BookPackageRepository extends JpaRepository<BookPackage, Integer> {
	
	List<BookPackage> findAllByStatus(BookPackageStatus status);
	
	Optional<BookPackage> findByBookAndItems(Book book, Collection<BookItem> items);
	
	List<BookPackage> findAllByCategoryAndStatus(Category category, BookPackageStatus status);
	
	List<BookPackage> findAllByModeAndStatus(int mode, BookPackageStatus status);
	
	List<BookPackage> findAllByBookIn(Collection<Book> books);
	
	List<BookPackage> findAllByCategoryAndBookIn(Category category, Collection<Book> books);
	
	List<BookPackage> findAllByModeAndBookIn(int mode, Collection<Book> books);

	List<BookPackage> findAllByModeAndCategoryAndBookIn (int value, Category category, Collection<Book> booksByTitle);
	

}
