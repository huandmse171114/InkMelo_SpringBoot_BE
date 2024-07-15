package com.inkmelo.bookpackage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.Book;
import com.inkmelo.book.BookMappingService;
import com.inkmelo.book.BookRepository;
import com.inkmelo.bookitem.BookItem;
import com.inkmelo.bookitem.BookItemMappingService;
import com.inkmelo.bookitem.BookItemRepository;
import com.inkmelo.bookitem.BookItemStatus;
import com.inkmelo.bookitem.BookItemType;
import com.inkmelo.category.Category;
import com.inkmelo.category.CategoryMappingService;
import com.inkmelo.category.CategoryRepository;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemFoundException;
import com.inkmelo.exception.NoCategoryFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookPackageMappingService {
	
	private final BookMappingService bookMappingService;
	private final BookItemMappingService bookItemMappingService;
	private final CategoryMappingService categoryMappingService;
	private final BookRepository bookRepository;
	private final BookItemRepository bookItemRepository;
	private final CategoryRepository categoryRepository;
	private int minStock;
	
	public BookPackageModeResponseDTO bookPackageModeToBookPackageModeDTO(BookPackageMode bookPackageMode) {
		return BookPackageModeResponseDTO.builder()
				.id(bookPackageMode.getValue())
				.description(bookPackageMode.getDescription())
				.build();
	}
	
	public BookPackageResponseDTO bookPackageToBookPackageResponseDTO(BookPackage bookPackage) {
		return BookPackageResponseDTO.builder()
				.id(bookPackage.getId())
				.title(bookPackage.getTitle())
				.description(bookPackage.getDescription())
				.price(bookPackage.getPrice())
				.modeId(bookPackage.getMode())
				.stock(bookPackage.getStock())
				.book(bookMappingService
						.bookToBookResponseDTO(bookPackage.getBook()))
				.items(bookPackage.getItems().stream()
						.filter(item -> item.getStatus() == BookItemStatus.ACTIVE)
						.map(item -> 
							bookItemMappingService.bookItemToBookItemResponseDTO(item))
						.toList())
				.category(categoryMappingService
						.categoryToCategoryResponseDTO(bookPackage.getCategory()))
				.build();
	}
	
	public BookPackageOrderResponseDTO bookPackageToBookPackageOrderResponseDTO(BookPackage bookPackage) {
		return BookPackageOrderResponseDTO.builder()
				.id(bookPackage.getId())
				.title(bookPackage.getTitle())
				.description(bookPackage.getDescription())
				.price(bookPackage.getPrice())
				.modeId(bookPackage.getMode())
				.stock(bookPackage.getStock())
				.book(bookMappingService
						.bookToBookOrderResponseDTO(bookPackage.getBook()))
				.items(bookPackage.getItems().stream()
						.filter(item -> item.getStatus() == BookItemStatus.ACTIVE)
						.map(item -> 
							bookItemMappingService.bookItemToBookItemOrderResponseDTO(item))
						.toList())
				.category(categoryMappingService
						.categoryToCategoryResponseDTO(bookPackage.getCategory()))
				.build();
	}
	
	public BookPackageAdminResponseDTO bookPackageToBookPackageAdminResponseDTO(BookPackage bookPackage) {
		return BookPackageAdminResponseDTO.builder()
				.id(bookPackage.getId())
				.title(bookPackage.getTitle())
				.description(bookPackage.getDescription())
				.price(bookPackage.getPrice())
				.modeId(bookPackage.getMode())
				.stock(bookPackage.getStock())
				.book(bookMappingService
						.bookToBookResponseDTO(bookPackage.getBook()))
				.items(bookPackage.getItems().stream()
						.map(item -> 
							bookItemMappingService.bookItemToBookItemResponseDTO(item))
						.toList())
				.category(categoryMappingService
						.categoryToCategoryResponseDTO(bookPackage.getCategory()))
				.createdAt(bookPackage.getCreatedAt())
				.lastChangedBy(bookPackage.getLastChangedBy())
				.lastUpdatedTime(bookPackage.getLastUpdatedTime())
				.status(bookPackage.getStatus())
				.build();
	}

	public BookPackage bookPackageCreateBodyDTOToBookPackage(BookPackageCreateBodyDTO bookPackageDTO) {
		
		Optional<Book> bookOption = bookRepository.findById(bookPackageDTO.bookId());
		
		if (bookOption.isEmpty()) {
			throw new NoBookFoundException("Tạo mới gói tài nguyên sách thất bại. Cuốn sách không tồn tại.");
		}
		
		Book book = bookOption.get();
		List<BookItem> bookItems = bookItemRepository.findAllByIdIn(bookPackageDTO.itemIds());
		
		
		if (bookItems.size() < bookPackageDTO.itemIds().size()) {
			throw new NoBookItemFoundException("Tạo mới gói tài nguyên sách thất bại. Một số tài nguyên sách không tồn tại.");
		}
		
		int mode = bookPackageDTO.modeId();
		minStock = 9999999;
//		Get stock value
		if (mode != BookPackageMode.AUDIO.getValue()
				& mode != BookPackageMode.PDF.getValue()
				& mode != BookPackageMode.AUDIOPDF.getValue()) {
			bookItems.forEach(item -> {
				if (item.getType() == BookItemType.PAPER) {
					if (minStock > item.getStock()) {
						minStock = item.getStock();
					}
				}
			});
		}else {
			minStock = 1;
		}
		
		
		Optional<Category> categoryOption = categoryRepository.findById(bookPackageDTO.categoryId());
		
		if (categoryOption.isEmpty()) {
			throw new NoCategoryFoundException("Tạo mới gói tài nguyên sách thất bại. Danh mục sách không tồn tại.");
		}
		
		Category category = categoryOption.get();
		
		return BookPackage.builder()
				.title(bookPackageDTO.title())
				.description(bookPackageDTO.description())
				.price(bookPackageDTO.price())
//				không truyền thẳng giá trị vào để kiểm tra xem giá trị có hợp lệ không
				.mode(BookPackageMode.fromValue(
							bookPackageDTO.modeId()
						).getValue())
				.book(book)
				.category(category)
				.items(bookItems)
				.stock(minStock)
				.lastChangedBy(SecurityContextHolder.getContext()
						.getAuthentication() != null ? SecurityContextHolder.getContext()
								.getAuthentication().getName() : "anonymous")
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.status(BookPackageStatus.ACTIVE)
				.build();
	}
	
}
