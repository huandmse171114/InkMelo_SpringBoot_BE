package com.inkmelo.bookpackage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.Book;
import com.inkmelo.book.BookRepository;
import com.inkmelo.bookitem.BookItem;
import com.inkmelo.bookitem.BookItemRepository;
import com.inkmelo.bookitem.BookItemType;
import com.inkmelo.category.Category;
import com.inkmelo.category.CategoryRepository;
import com.inkmelo.exception.DuplicateBookPackageException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemFoundException;
import com.inkmelo.exception.NoBookPackageExistException;
import com.inkmelo.exception.NoBookPackageFoundException;
import com.inkmelo.exception.NoCategoryFoundException;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.genre.Genre;
import com.inkmelo.genre.GenreRepository;
import com.inkmelo.utils.Utils;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BookPackageService {
	private final BookPackageRepository repository;
	private final BookPackageMappingService mappingService;
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final BookItemRepository bookItemRepository;
	private final GenreRepository genreRepository;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;
	private int minStock;

	public ResponseEntity<?> findAllBookPackageByStatus(BookPackageStatus status, Integer page, 
			Integer size, Integer categoryId, Integer genreId, Integer modeId, String keyword) {
		
		List<BookPackage> bookPackages;
		
//		Case 1: search by category
		if (categoryId != null & modeId == null & keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByCategory(status, categoryId);
//		Caes 2: search by mode
		}else if (categoryId == null & modeId != null & keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByMode(status, modeId);
//		Case 3: search by keyword
		}else if (categoryId == null & modeId == null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthor(status, keyword);
//		Case 4: search by genre
		} else if (categoryId == null & modeId == null & keyword.isEmpty() & genreId != null) {
			bookPackages = findAllBookPackageByGenre(status, genreId);
//		Case 5: search by category and keyword
		}else if (categoryId != null & modeId == null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthorAndCategory(status, keyword, categoryId);
//		Case 6: search by mode and keyword
		}else if (categoryId == null & modeId != null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthorAndMode(status, keyword, modeId);
//		Case 7: search by category, mode, and keyword
		}else if (categoryId != null & modeId != null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthorAndCategoryAndMode(status, keyword, categoryId, modeId);
//		Case 8: search by genre and keyword
		}else if (categoryId == null & modeId == null & !keyword.isEmpty() & genreId != null) {
			bookPackages = findAllBookPackageByKeywordAndGenre(status, genreId, keyword);
//		Case 9: search by category, keyword, and genre
		}else if (categoryId != null & modeId == null & !keyword.isEmpty() & genreId != null) {
			bookPackages = findAllByCategoryAndKeywordAndGenre(status, categoryId, genreId, keyword);
//		Case 10: search by category and genre
		}else if (categoryId != null & modeId == null & keyword.isEmpty() & genreId != null) {
			bookPackages = findAllByCategoryAndGenre(status, categoryId, genreId);
//		Case 11: not support case will return all data
		}else {
			bookPackages = repository.findAllByStatusOrderByIdAsc(status);
		}
		
		var response = getResponse(bookPackages);
		
		if (response.isEmpty()) {
			throw new NoBookPackageFoundException("Dữ liệu về gói tài nguyên sách hiện đang rỗng hoặc không tìm thấy.");
		}
		
		if (page == null & size == null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			int listSize = response.size();
			int totalPages = (int) Math.ceil((double) listSize / (double) size);
			
			if (totalPages <= page) 
				throw new NoBookPackageFoundException("Dữ liệu về gói tài nguyên sách hiện đang rỗng hoặc không tìm thấy.");
			
			var responseDTO = response.subList(page*size, Math.min(page*size + size, listSize));
			
			return Utils.generatePagingListResponseEntity(
					listSize, 
					responseDTO, 
					totalPages, 
					page,
					HttpStatus.OK);
		}
		
	}
	
	public ResponseEntity<?> findAllBookPackage(Integer page, Integer size, 
			Integer categoryId, Integer genreId, Integer modeId, String keyword) {
		
		List<BookPackage> bookPackages;
		
//		Case 1: search by category
		if (categoryId != null & modeId == null & keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByCategory(null, categoryId);
//		Caes 2: search by mode
		}else if (categoryId == null & modeId != null & keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByMode(null, modeId);
//		Case 3: search by keyword
		}else if (categoryId == null & modeId == null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthor(null, keyword);
//		Case 4: search by genre
		} else if (categoryId == null & modeId == null & keyword.isEmpty() & genreId != null) {
			bookPackages = findAllBookPackageByGenre(null, genreId);
//		Case 5: search by category and keyword
		}else if (categoryId != null & modeId == null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthorAndCategory(null, keyword, categoryId);
//		Case 6: search by mode and keyword
		}else if (categoryId == null & modeId != null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthorAndMode(null, keyword, modeId);
//		Case 7: search by category, mode, and keyword
		}else if (categoryId != null & modeId != null & !keyword.isEmpty() & genreId == null) {
			bookPackages = findAllBookPackageByBookTitleOrAuthorAndCategoryAndMode(null, keyword, categoryId, modeId);
//		Case 8: search by genre and keyword
		}else if (categoryId == null & modeId == null & !keyword.isEmpty() & genreId != null) {
			bookPackages = findAllBookPackageByKeywordAndGenre(null, genreId, keyword);
//		Case 9: search by category, keyword, and genre
		}else if (categoryId != null & modeId == null & !keyword.isEmpty() & genreId != null) {
			bookPackages = findAllByCategoryAndKeywordAndGenre(null, categoryId, genreId, keyword);
//		Case 10: search by category and genre
		}else if (categoryId != null & modeId == null & keyword.isEmpty() & genreId != null) {
			bookPackages = findAllByCategoryAndGenre(null, categoryId, genreId);
//		Case 11: not support case will return all data
		}else {
			bookPackages = repository.findAllByOrderById();
		}
		
		var response = getAdminResponse(bookPackages);
		
		if (page == null & size == null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			int listSize = response.size();
			int totalPages = (int) Math.ceil((double) listSize / (double) size);
			
			var responseDTO = response.subList(page*size, Math.min(page*size + size, listSize));
			
			return Utils.generatePagingListResponseEntity(
					listSize, 
					responseDTO, 
					totalPages, 
					page,
					HttpStatus.OK);
		}
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
		
		if (!oldItemIds
				.containsAll(bookPackageDTO.itemIds()) |
				!bookPackageDTO.itemIds().containsAll(oldItemIds)) {
			List<BookItem> newItems = bookItemRepository.findAllByIdInOrderByIdAsc(bookPackageDTO.itemIds());
			
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

	public List<BookPackageResponseDTO> findAllBookPackageByCriteria(Integer categoryId, Integer genreId, Integer modeId, String keyword) {
		return null;
	}
	
	private List<BookPackage> findAllBookPackageByCategory(BookPackageStatus status, Integer categoryId) {
		
		Category category = checkExistCategory(categoryId);
		
		List<BookPackage> bookPackages = new ArrayList<>();
		
		if (status != null) {
			bookPackages = repository.findAllByCategoryAndStatusOrderByIdAsc(category, status);			
		}else {
			bookPackages = repository.findAllByCategoryOrderByIdAsc(category);	
		}
		
		return bookPackages;
	}
	
	private List<BookPackage> findAllBookPackageByMode(BookPackageStatus status,  Integer modeId) {
		
		BookPackageMode mode = BookPackageMode.fromValue(modeId);
		
		List<BookPackage> bookPackages = new ArrayList<>();
		
		if (status != null) {
			bookPackages = repository.findAllByModeAndStatusOrderByIdAsc(mode.getValue(), status);			
		}else {
			bookPackages = repository.findAllByModeOrderByIdAsc(mode.getValue());			
		}
		
		
		return bookPackages;
	}
	
	private List<BookPackage> findAllBookPackageByBookTitleOrAuthor(BookPackageStatus status, String keyword) {
		var booksByTitle = bookRepository.findAllByTitleContainingIgnoreCaseOrderByIdAsc(keyword);
		
		var booksByAuthor = bookRepository.findAllByAuthorContainingIgnoreCaseOrderByIdAsc(keyword);
		
		List<BookPackage> bookPackagesByBookTitle = new ArrayList<>();
		List<BookPackage> bookPackagesByBookAuthor = new ArrayList<>();
		
		if (status != null) {
			bookPackagesByBookTitle = repository.findAllByBookInAndStatusOrderByIdAsc(booksByTitle, status);
			bookPackagesByBookAuthor = repository.findAllByBookInAndStatusOrderByIdAsc(booksByAuthor, status);			
		}else {
			bookPackagesByBookTitle = repository.findAllByBookInOrderByIdAsc(booksByTitle);
			bookPackagesByBookAuthor = repository.findAllByBookInOrderByIdAsc(booksByAuthor);	
		}
		
		
		Set<BookPackage> response = new LinkedHashSet<>();
		
		response.addAll(bookPackagesByBookTitle);
		response.addAll(bookPackagesByBookAuthor);
		
		return response.stream().toList();
	}
	
	private List<BookPackage> findAllBookPackageByGenre(BookPackageStatus status, Integer genreId) {
		
		List<Genre> genres = new ArrayList<>();
		genres.add(checkExistGenre(genreId));
		
		List<Book> books = bookRepository.findAllByGenresOrderByIdAsc(genres);
		
		List<BookPackage> bookPackages = new ArrayList<>();
		
		if (status != null) {
			bookPackages = repository.findAllByBookInAndStatusOrderByIdAsc(books, status);			
		}else {
			bookPackages = repository.findAllByBookInOrderByIdAsc(books);
		}
		
		return bookPackages;
		
	}
	
	private List<BookPackage> findAllBookPackageByBookTitleOrAuthorAndCategory(BookPackageStatus status, String keyword, Integer categoryId) {
		var booksByTitle = bookRepository.findAllByTitleContainingIgnoreCaseOrderByIdAsc(keyword);
		
		var booksByAuthor = bookRepository.findAllByAuthorContainingIgnoreCaseOrderByIdAsc(keyword);
		
		Category category = checkExistCategory(categoryId);
		
		List<BookPackage> bookPackagesByBookTitle = new ArrayList<>();
		List<BookPackage> bookPackagesByBookAuthor = new ArrayList<>();
		
		if (status != null) {
			bookPackagesByBookTitle = repository.findAllByCategoryAndBookInAndStatusOrderByIdAsc(category, booksByTitle, status);
			bookPackagesByBookAuthor = repository.findAllByCategoryAndBookInAndStatusOrderByIdAsc(category, booksByAuthor, status);			
		}else {
			bookPackagesByBookTitle = repository.findAllByCategoryAndBookInOrderByIdAsc(category, booksByTitle);
			bookPackagesByBookAuthor = repository.findAllByCategoryAndBookInOrderByIdAsc(category, booksByAuthor);			
		}
		
		
		Set<BookPackage> response = new LinkedHashSet<>();
		
		response.addAll(bookPackagesByBookTitle);
		response.addAll(bookPackagesByBookAuthor);
		
		return response.stream().toList();
	}
	
	private List<BookPackage> findAllBookPackageByBookTitleOrAuthorAndMode(BookPackageStatus status, String keyword, Integer modeId) {
		var booksByTitle = bookRepository.findAllByTitleContainingIgnoreCaseOrderByIdAsc(keyword);
		
		var booksByAuthor = bookRepository.findAllByAuthorContainingIgnoreCaseOrderByIdAsc(keyword);
		
		BookPackageMode mode = BookPackageMode.fromValue(modeId);
		
		List<BookPackage> bookPackagesByBookTitle = new ArrayList<>();
		List<BookPackage> bookPackagesByBookAuthor = new ArrayList<>();
		
		if (status != null) {
			bookPackagesByBookTitle = repository.findAllByModeAndBookInAndStatusOrderByIdAsc(mode.getValue(), booksByTitle, status);
			bookPackagesByBookAuthor = repository.findAllByModeAndBookInAndStatusOrderByIdAsc(mode.getValue(), booksByAuthor, status);
		}else {
			bookPackagesByBookTitle = repository.findAllByModeAndBookInOrderByIdAsc(mode.getValue(), booksByTitle);
			bookPackagesByBookAuthor = repository.findAllByModeAndBookInOrderByIdAsc(mode.getValue(), booksByAuthor);
		}
		
		
		Set<BookPackage> response = new LinkedHashSet<>();
		
		response.addAll(bookPackagesByBookTitle);
		response.addAll(bookPackagesByBookAuthor);
		
		return response.stream().toList();
	}
	
	private List<BookPackage> findAllBookPackageByBookTitleOrAuthorAndCategoryAndMode(BookPackageStatus status, String keyword, Integer categoryId, Integer modeId) {
		var booksByTitle = bookRepository.findAllByTitleContainingIgnoreCaseOrderByIdAsc(keyword);
		
		var booksByAuthor = bookRepository.findAllByAuthorContainingIgnoreCaseOrderByIdAsc(keyword);
		
		BookPackageMode mode = BookPackageMode.fromValue(modeId);
		
		Category category = checkExistCategory(categoryId);
		
		List<BookPackage> bookPackagesByBookTitle = new ArrayList<>();
		List<BookPackage> bookPackagesByBookAuthor = new ArrayList<>();
		
		if (status != null) {
			bookPackagesByBookTitle = repository.findAllByModeAndCategoryAndBookInAndStatusOrderByIdAsc(mode.getValue(), category, booksByTitle, status);
			bookPackagesByBookAuthor = repository.findAllByModeAndCategoryAndBookInAndStatusOrderByIdAsc(mode.getValue(), category, booksByAuthor, status);
		}else {
			bookPackagesByBookTitle = repository.findAllByModeAndCategoryAndBookInOrderByIdAsc(mode.getValue(), category, booksByTitle);
			bookPackagesByBookAuthor = repository.findAllByModeAndCategoryAndBookInOrderByIdAsc(mode.getValue(), category, booksByAuthor);
		}
		
		
		Set<BookPackage> response = new LinkedHashSet<>();
		
		response.addAll(bookPackagesByBookTitle);
		response.addAll(bookPackagesByBookAuthor);
		
		return response.stream().toList();
	}
	
	private List<BookPackage> findAllBookPackageByKeywordAndGenre(BookPackageStatus status, Integer genreId, String keyword) {
		
		List<Genre> genres = new ArrayList<>();
		genres.add(checkExistGenre(genreId));
		
		var booksByTitle = bookRepository.findAllByTitleContainingIgnoreCaseAndGenresOrderByIdAsc(keyword, genres);
		
		var booksByAuthor = bookRepository.findAllByAuthorContainingIgnoreCaseAndGenresOrderByIdAsc(keyword, genres);
		
		List<BookPackage> bookPackagesByBookTitle = new ArrayList<>();
		List<BookPackage> bookPackagesByBookAuthor = new ArrayList<>();
		
		if (status != null) {
			bookPackagesByBookTitle = repository.findAllByBookInAndStatusOrderByIdAsc(booksByTitle, status);
			bookPackagesByBookAuthor = repository.findAllByBookInAndStatusOrderByIdAsc(booksByAuthor, status);
		}else {
			bookPackagesByBookTitle = repository.findAllByBookInOrderByIdAsc(booksByTitle);
			bookPackagesByBookAuthor = repository.findAllByBookInOrderByIdAsc(booksByAuthor);
		}
		
		Set<BookPackage> response = new LinkedHashSet<>();
		
		response.addAll(bookPackagesByBookTitle);
		response.addAll(bookPackagesByBookAuthor);
		
		return response.stream().toList();
	}
	
	private List<BookPackage> findAllByCategoryAndKeywordAndGenre(BookPackageStatus status, Integer categoryId, Integer genreId, String keyword) {
		
		Category category = checkExistCategory(categoryId);
		
		List<Genre> genres = new ArrayList<>();
		genres.add(checkExistGenre(genreId));
		
		var booksByTitleAndGenre = bookRepository.findAllByTitleContainingIgnoreCaseAndGenresOrderByIdAsc(keyword, genres);
		
		var booksByAuthorAndGenre = bookRepository.findAllByAuthorContainingIgnoreCaseAndGenresOrderByIdAsc(keyword, genres);
		
		List<BookPackage> bookPackagesByBookTitleAndGenre = new ArrayList<>();
		List<BookPackage> bookPackagesByBookAuthorAndGenre = new ArrayList<>();
		
		if (status != null) {
			bookPackagesByBookTitleAndGenre = repository.findAllByCategoryAndBookInAndStatusOrderByIdAsc(category, booksByTitleAndGenre, status);
			bookPackagesByBookAuthorAndGenre = repository.findAllByCategoryAndBookInAndStatusOrderByIdAsc(category, booksByAuthorAndGenre, status);
		}else {
			bookPackagesByBookTitleAndGenre = repository.findAllByCategoryAndBookInOrderByIdAsc(category, booksByTitleAndGenre);
			bookPackagesByBookAuthorAndGenre = repository.findAllByCategoryAndBookInOrderByIdAsc(category, booksByAuthorAndGenre);
		}
		
		Set<BookPackage> response = new LinkedHashSet<>();
		
		response.addAll(bookPackagesByBookTitleAndGenre);
		response.addAll(bookPackagesByBookAuthorAndGenre);
		
		return response.stream().toList();
	}
	
	private List<BookPackage> findAllByCategoryAndGenre(BookPackageStatus status, Integer categoryId, Integer genreId) {
		
		Category category = checkExistCategory(categoryId);
		
		List<Genre> genres = new ArrayList<>();
		genres.add(checkExistGenre(genreId));
		
		List<Book> booksByGenre = bookRepository.findAllByGenresOrderByIdAsc(genres);
		
		List<BookPackage> bookPackages = new ArrayList<>();
		
		if (status != null) {
			bookPackages = repository.findAllByCategoryAndBookInAndStatusOrderByIdAsc(category, booksByGenre, status);
		}else {
			bookPackages = repository.findAllByCategoryAndBookInOrderByIdAsc(category, booksByGenre);
		}
		
		
		return bookPackages;
		
	
	}
	
	private Category checkExistCategory(Integer id) {
		Optional<Category> categoryOption = categoryRepository.findById(id);
		
		if (categoryOption.isEmpty()) {
			throw new NoCategoryFoundException("Tìm kiếm tài nguyên sách thất bại. Danh mục không tồn tại");
		}
		
		return categoryOption.get();
		
	}
	
	private Genre checkExistGenre(Integer id) {
		Optional<Genre> genreOption = genreRepository.findById(id);
		
		if (genreOption.isEmpty()) {
			throw new NoGenreFoundException("Tìm kiếm tài nguyên sách thất bại. Thể loại không tồn tại.");
		}
		
		return genreOption.get();
	}
	
	private List<BookPackageResponseDTO> getResponse(Collection<BookPackage> bookPackages) {
		if (bookPackages.isEmpty()) {
			throw new NoBookPackageFoundException("Không có tài nguyên sách phù hợp với tìm kiếm.");
		}
		
		return bookPackages.stream()
				.map(item -> mappingService.bookPackageToBookPackageResponseDTO(item))
				.toList();
	}
	
	private List<BookPackageAdminResponseDTO> getAdminResponse(Collection<BookPackage> bookPackages) {
		if (bookPackages.isEmpty()) {
			throw new NoBookPackageFoundException("Không có tài nguyên sách phù hợp với tìm kiếm.");
		}
		
		return bookPackages.stream()
				.map(item -> mappingService.bookPackageToBookPackageAdminResponseDTO(item))
				.toList();
	}
	
	public void updateStock(BookPackage bookPackage) {
		int mode = bookPackage.getMode();
		minStock = 9999999;
		List<BookItem> bookItems = bookPackage.getItems();
		
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
		
		bookPackage.setStock(minStock);
		bookPackage.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		bookPackage.setLastChangedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		
		repository.save(bookPackage);
	}

	public ResponseEntity<?> findBookPackageByIdAndStatus(Integer id, BookPackageStatus status) {
		
		Optional<BookPackage> bookPackageOptional = repository.findByIdAndStatus(id, status);
		
		if (bookPackageOptional.isEmpty()) {
			throw new NoBookPackageFoundException("Không tìm thấy gói sách tương ứng.");
		}
		
		BookPackage bookPackage = bookPackageOptional.get();
		
		var response = new HashMap<String, Object>();
		
		response.put("data", mappingService.bookPackageToBookPackageResponseDTO(bookPackage));
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
}
