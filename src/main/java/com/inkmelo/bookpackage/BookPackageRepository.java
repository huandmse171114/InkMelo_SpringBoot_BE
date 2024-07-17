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
	
	List<BookPackage> findAllByStatusOrderByIdAsc(BookPackageStatus status);
	
	Optional<BookPackage> findByBookAndItemsAndStatus(Book book, Collection<BookItem> items, BookPackageStatus status);
	
	List<BookPackage> findAllByCategoryAndStatusOrderByIdAsc(Category category, BookPackageStatus status);
	
	List<BookPackage> findAllByCategoryOrderByIdAsc(Category category);
	
	List<BookPackage> findAllByModeAndStatusOrderByIdAsc(int mode, BookPackageStatus status);
	
	List<BookPackage> findAllByModeOrderByIdAsc(int mode);
	
	List<BookPackage> findAllByBookInAndStatusOrderByIdAsc(Collection<Book> books, BookPackageStatus status);
	
	List<BookPackage> findAllByBookInOrderByIdAsc(Collection<Book> books);
	
	List<BookPackage> findAllByCategoryAndBookInAndStatusOrderByIdAsc(Category category, Collection<Book> books, BookPackageStatus status);
	
	List<BookPackage> findAllByCategoryAndBookInOrderByIdAsc(Category category, Collection<Book> books);
	
	List<BookPackage> findAllByModeAndBookInAndStatusOrderByIdAsc(int mode, Collection<Book> books, BookPackageStatus status);
	
	List<BookPackage> findAllByModeAndBookInOrderByIdAsc(int mode, Collection<Book> books);

	List<BookPackage> findAllByModeAndCategoryAndBookInAndStatusOrderByIdAsc(int value, Category category, Collection<Book> booksByTitle, BookPackageStatus status);
	
	List<BookPackage> findAllByModeAndCategoryAndBookInOrderByIdAsc(int value, Category category, Collection<Book> booksByTitle);

}
