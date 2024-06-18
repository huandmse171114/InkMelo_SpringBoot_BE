package com.inkmelo.bookpackage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.Book;
import com.inkmelo.book.BookRepository;
import com.inkmelo.bookitem.BookItem;
import com.inkmelo.bookitem.BookItemRepository;
import com.inkmelo.category.Category;
import com.inkmelo.category.CategoryRepository;
import com.inkmelo.exception.DuplicateBookPackageException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemFoundException;
import com.inkmelo.exception.NoBookPackageExistException;
import com.inkmelo.exception.NoBookPackageFoundException;
import com.inkmelo.exception.NoCategoryFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BookPackageService {
	private final BookPackageRepository repository;
	private final BookPackageMappingService mappingService;
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final BookItemRepository bookItemRepository;

	public List<BookPackageResponseDTO> findAllBookPackageByStatus(BookPackageStatus status) {
		var bookPackages = repository.findAllByStatus(status);
		
		if (bookPackages.isEmpty()) {
			throw new NoBookPackageExistException("Dữ liệu về gói tài nguyên sách hiện đang rỗng.");
		}
		
		return bookPackages.stream()
				.map(bookPackage -> mappingService.bookPackageToBookPackageResponseDTO(bookPackage))
				.sorted(new Comparator<BookPackageResponseDTO>() {
					@Override
					public int compare(BookPackageResponseDTO o1, BookPackageResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}
	
	public List<BookPackageAdminResponseDTO> findAllBookPackage() {
		var bookPackages = repository.findAll();
		
		if (bookPackages.isEmpty()) {
			throw new NoBookPackageExistException("Dữ liệu về gói tài nguyên sách hiện đang rỗng.");
		}
		
		return bookPackages.stream()
				.map(bookPackage -> mappingService.bookPackageToBookPackageAdminResponseDTO(bookPackage))
				.sorted(new Comparator<BookPackageAdminResponseDTO>() {
					@Override
					public int compare(BookPackageAdminResponseDTO o1, BookPackageAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}

	public Set<BookPackageStatus> findAllBookPackageStatus() {
		return BookPackageStatus.allStatus;
	}

	public List<BookPackageModeResponseDTO> findAllBookPackageMode() {
		Set<BookPackageMode> modes = BookPackageMode.allMode;
		
		return modes.stream()
				.map(mode -> mappingService.bookPackageModeToBookPackageModeDTO(mode))
				.toList();
	}

	public void saveBookPackage(BookPackageCreateBodyDTO bookPackageDTO) {
		
		BookPackage bookPackage = mappingService
				.bookPackageCreateBodyDTOToBookPackage(bookPackageDTO);
		
//		Cần xem lại
//		Optional<BookPackage> bookPackageDB = repository
//				.findByBookAndItems(bookPackage.getBook(), bookPackage.getItems());
//		
//		if (bookPackageDB.isPresent()) {
//			throw new DuplicateBookPackageException("Tạo mới gói tài nguyên sách thất bại. Gói tài nguyên sách" 
//					+ " của cuốn sách " + bookPackage.getBook().getTitle()
//					+ " với các thành phần tài nguyên tương tự đã tồn tại. "
//					+ "Vui lòng thực hiện chỉnh sửa thay cho hành động tạo mới.");
//		}
		
		repository.save(bookPackage);
		
	}

	public void updateBookPackage(BookPackageUpdateBodyDTO bookPackageDTO) {
		
		var bookPackageOption = repository.findById(bookPackageDTO.id());
		
		if (bookPackageOption.isEmpty()) {
			throw new NoBookPackageFoundException(bookPackageDTO.id());
		}
		
		BookPackage bookPackage = bookPackageOption.get();
		
		bookPackage.setTitle(bookPackageDTO.title());
		bookPackage.setDescription(bookPackageDTO.description());
		bookPackage.setPrice(bookPackageDTO.price());
		bookPackage.setMode(BookPackageMode
				.fromValue(bookPackageDTO.modeId()).getValue());
		bookPackage.setLastChangedBy(SecurityContextHolder
				.getContext().getAuthentication().getName());
		bookPackage.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		bookPackage.setStatus(bookPackageDTO.status());
		
		if (bookPackage.getBook().getId() != bookPackageDTO.bookId()) {
			Optional<Book> newBookOption = bookRepository.findById(bookPackageDTO.bookId());
			
			if (newBookOption.isEmpty()) {
				throw new NoBookFoundException("Cập nhật gói tài nguyên sách thất bại. Cuốn sách không tồn tại.");
			}
			
			bookPackage.setBook(newBookOption.get());
			
		}
		
		if (bookPackage.getCategory().getId() != bookPackageDTO.categoryId()) {
			Optional<Category> categoryOption = categoryRepository.findById(bookPackageDTO.categoryId());
			
			if (categoryOption.isEmpty()) {
				throw new NoCategoryFoundException("Cập nhật gói tài nguyên sách thất bại. Danh mục sách không tồn tại.");
			}
			
			bookPackage.setCategory(categoryOption.get());
		}
		
		List<Integer> oldItemIds = bookPackage.getItems()
				.stream()
				.map(item -> item.getId())
				.toList();
		
		if (oldItemIds
				.containsAll(bookPackageDTO.itemIds()) |
				!bookPackageDTO.itemIds().containsAll(oldItemIds)) {
			List<BookItem> newItems = bookItemRepository.findAllByIdIn(bookPackageDTO.itemIds());
			
			if (newItems.size() < bookPackageDTO.itemIds().size()) {
				throw new NoBookItemFoundException("Cập nhật gói tài nguyên sách thất bại. Một số tài nguyên sách không tồn tại.");
			}
			
			bookPackage.setItems(newItems);
		}
		
		
		repository.save(bookPackage);
	}

	public void deleteBookPackageById(Integer id) {
		var bookPackageOption = repository.findById(id);
		
		if (bookPackageOption.isEmpty()) {
			throw new NoBookPackageFoundException(id);
		}
		
		BookPackage bookPackage = bookPackageOption.get();
		
		bookPackage.setStatus(BookPackageStatus.INACTIVE);
		bookPackage.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		bookPackage.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		
		repository.save(bookPackage);
		
	}
	
}
