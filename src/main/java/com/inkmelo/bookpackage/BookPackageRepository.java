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
	
	Optional<BookPackage> findByIdAndStatus(Integer id, BookPackageStatus status);
	
	List<BookPackage> findAllByStatus(BookPackageStatus status);
	
	Optional<BookPackage> findByBookAndItemsAndStatus(Book book, Collection<BookItem> items, BookPackageStatus status);
	
	List<BookPackage> findAllByCategoryAndStatus(Category category, BookPackageStatus status);
	
	List<BookPackage> findAllByCategory(Category category);
	
	List<BookPackage> findAllByModeAndStatus(int mode, BookPackageStatus status);
	
	List<BookPackage> findAllByMode(int mode);
	
	List<BookPackage> findAllByBookInAndStatus(Collection<Book> books, BookPackageStatus status);
	
	List<BookPackage> findAllByBookIn(Collection<Book> books);
	
	List<BookPackage> findAllByCategoryAndBookInAndStatus(Category category, Collection<Book> books, BookPackageStatus status);
	
	List<BookPackage> findAllByCategoryAndBookIn(Category category, Collection<Book> books);
	
	List<BookPackage> findAllByModeAndBookInAndStatus(int mode, Collection<Book> books, BookPackageStatus status);
	
	List<BookPackage> findAllByModeAndBookIn(int mode, Collection<Book> books);

	List<BookPackage> findAllByModeAndCategoryAndBookInAndStatus(int value, Category category, Collection<Book> booksByTitle, BookPackageStatus status);
	
	List<BookPackage> findAllByModeAndCategoryAndBookIn(int value, Category category, Collection<Book> booksByTitle);

}
