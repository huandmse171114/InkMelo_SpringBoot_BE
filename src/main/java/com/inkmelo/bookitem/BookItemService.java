package com.inkmelo.bookitem;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.BookRepository;
import com.inkmelo.book.BookStatus;
import com.inkmelo.exception.DuplicatedBookItemException;
import com.inkmelo.exception.InvalidBookItemFieldValueException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemExistException;
import com.inkmelo.exception.NoBookItemFoundException;
import com.inkmelo.utils.Utils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookItemService {
	
	private final BookItemRepository repository;
	private final BookRepository bookRepository;
	private final BookItemMappingService mappingService;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;

	public ResponseEntity<?> findAllBookItemByStatus(BookItemStatus status, Integer page, Integer size, 
			BookItemType type, String title) throws NoBookItemExistException {
		
//		Get books, without paging
		if (page == null & size == null) {
			
			List<BookItem> bookItems;
			
			var books = bookRepository.findAllByStatusAndTitleContainingIgnoreCase(BookStatus.ACTIVE, title);
			
			if (type != null) {
				bookItems = repository.findAllByStatusAndTypeAndBookIn(status, type, books);				
			} else {
				bookItems = repository.findAllByStatusAndBookIn(status, books);
			}
			
			if (bookItems.isEmpty()) {
				throw new NoBookItemExistException("Dữ liệu về tài nguyên sách hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(bookItems.stream()
					.map(item -> mappingService.bookItemToBookItemResponseDTO(item))
					.sorted(new Comparator<BookItemResponseDTO>() {
						@Override
						public int compare(BookItemResponseDTO o1, BookItemResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
			
//		Get books, with paging
		}else {
			
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			Page<BookItem> pageBookItems;
			List<BookItem> bookItems;
			
			var books = bookRepository.findAllByStatusAndTitleContainingIgnoreCase(BookStatus.ACTIVE, title);
			
//			Search with type and book title
			if (type != null) {
				pageBookItems = repository.findAllByStatusAndTypeAndBookIn(status, type, books, paging);
				bookItems = pageBookItems.getContent();
				
//			Search with book title only
			} else {
				pageBookItems = repository.findAllByStatusAndBookIn(status, books, paging);
				bookItems = pageBookItems.getContent();
			}
			
			if (bookItems.isEmpty()) {
				throw new NoBookItemExistException("Dữ liệu về tài nguyên sách hiện đang rỗng.");
			}
			
			var response = bookItems.stream()
					.map(item -> mappingService.bookItemToBookItemResponseDTO(item))
					.sorted(new Comparator<BookItemResponseDTO>() {
						@Override
						public int compare(BookItemResponseDTO o1, BookItemResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList();
			
			return Utils.generatePagingListResponseEntity(
					pageBookItems.getTotalElements(), 
					response, 
					pageBookItems.getTotalPages(), 
					pageBookItems.getNumber(), 
					HttpStatus.OK);
			
		}
		
	}

	public ResponseEntity<?> findAllBookItem(Integer page, Integer size, BookItemType type, String title) 
		throws NoBookItemExistException {
		
//		Get books, without paging
		if (page == null & size == null) {
			
			List<BookItem> bookItems;
			
			var books = bookRepository.findAllByTitleContainingIgnoreCase(title);
			
			if (type != null) {
				bookItems = repository.findAllByTypeAndBookIn(type, books);				
			} else {
				bookItems = repository.findAllByBookIn(books);
			}
			
			if (bookItems.isEmpty()) {
				throw new NoBookItemExistException("Dữ liệu về tài nguyên sách hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(bookItems.stream()
					.map(item -> mappingService.bookItemToBookItemAdminResponseDTO(item))
					.sorted(new Comparator<BookItemAdminResponseDTO>() {
						@Override
						public int compare(BookItemAdminResponseDTO o1, BookItemAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
			
//		Get books, with paging
		}else {
			
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			Page<BookItem> pageBookItems;
			List<BookItem> bookItems;
			
			var books = bookRepository.findAllByTitleContainingIgnoreCase(title);
			
//			Search with type and book title
			if (type != null) {
				pageBookItems = repository.findAllByTypeAndBookIn(type, books, paging);
				bookItems = pageBookItems.getContent();
				
//			Search with book title only
			} else {
				pageBookItems = repository.findAllByBookIn(books, paging);
				bookItems = pageBookItems.getContent();
			}
			
			if (bookItems.isEmpty()) {
				throw new NoBookItemExistException("Dữ liệu về tài nguyên sách hiện đang rỗng.");
			}
			
			var response = bookItems.stream()
					.map(item -> mappingService.bookItemToBookItemAdminResponseDTO(item))
					.sorted(new Comparator<BookItemAdminResponseDTO>() {
						@Override
						public int compare(BookItemAdminResponseDTO o1, BookItemAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList();
			
			return Utils.generatePagingListResponseEntity(
					pageBookItems.getTotalElements(), 
					response, 
					pageBookItems.getTotalPages(), 
					pageBookItems.getNumber(), 
					HttpStatus.OK);
			
		}
		
	}

	public Set<BookItemType> findAllBookItemType() {
		return BookItemType.allType;
	}

	public Set<BookItemStatus> findAllBookItemStatus() {
		return BookItemStatus.allStatus;
	}

	public void saveBookItem(BookItemCreateBodyDTO bookItemDTO) 
		throws DataIntegrityViolationException, DuplicatedBookItemException {
		BookItem bookItem = mappingService.bookItemCreateBodyDTOToBookItem(bookItemDTO);
		
		Optional<BookItem> bookItemDB = repository.findByBookAndType(bookItem.getBook(), bookItem.getType());
		
		if (bookItemDB.isPresent()) {
			throw new DuplicatedBookItemException("Tạo mới tài nguyên sách thất bại. Tài nguyên sách loại " 
					+ bookItem.getType() 
					+ " đã tồn tại. Vui lòng thực hiện chỉnh sửa thay cho hành động tạo mới.");
		}
		
		// If new item is audio type, must check duration value
		try {
			if (bookItem.getType() == BookItemType.AUDIO) {
				if (bookItemDTO.duration() == null | bookItemDTO.duration() <= 0) {
					throw new InvalidBookItemFieldValueException(
						"Tạo mới tài nguyên sách thất bại. Thời lượng tệp audio không thể bé hơn hoặc bằng 0.");
				}
				
				bookItem.setDuration(bookItemDTO.duration());
			}
			
			
		} catch (NullPointerException e) {
			throw new InvalidBookItemFieldValueException(
					"Tạo mới tài nguyên sách thất bại. Thời lượng tệp audio không thể rỗng.");
		}
		
		// If new item is paper type, must check stock value
		try {
			if (bookItem.getType() == BookItemType.PAPER) {
				if (bookItemDTO.stock() <= 0) {
					throw new InvalidBookItemFieldValueException(
						"Tạo mới tài nguyên sách thất bại. Số lượng tồn kho sách bản cứng hiện đang bé hơn hoặc bằng 0.");
				}
				
				bookItem.setStock(bookItemDTO.stock());
			}
			
		} catch (NullPointerException e) {
			throw new InvalidBookItemFieldValueException(
					"Tạo mới tài nguyên sách thất bại. Số lượng tồn kho sách bản cứng hiện đang rỗng.");
		}
		
		
		// default stock value for audio and pdf are 1
		if (bookItem.getType() == BookItemType.AUDIO | bookItem.getType() == BookItemType.PDF) {
			bookItem.setStock(1); 
		}
		
		repository.save(bookItem);
		
	}

	public void updateBookItem(BookItemUpdateBodyDTO bookItemDTO) 
		throws DataIntegrityViolationException {
		
		var bookItemOption = repository.findById(bookItemDTO.id());
		
		if (bookItemOption.isEmpty()) {
			throw new NoBookItemFoundException(bookItemDTO.id());
		}
		
		BookItem bookItem = bookItemOption.get();
		bookItem.setSource(bookItemDTO.source());
		bookItem.setCreatedAt(Date.valueOf(LocalDate.now()));
		bookItem.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		bookItem.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		bookItem.setStatus(bookItemDTO.status());
		
		if (bookItem.getType() != bookItemDTO.type()) {
			Optional<BookItem> bookItemDB = repository.findByBookAndType(bookItem.getBook(), bookItem.getType());
			
			if (bookItemDB.isPresent()) {
				if (bookItemDB.get().getId() != bookItemDTO.id()) {
					throw new DuplicatedBookItemException("Cập nhật tài nguyên sách thất bại. Tài nguyên sách loại " 
							+ bookItem.getType() 
							+ " đã tồn tại. Vui lòng thực hiện chỉnh sửa thay cho hành động tạo mới.");
				}
			}
			
			bookItem.setType(bookItemDTO.type());
		}
		
		// Only paper item can update stock, audio and pdf cannot change default stock value
		try {
			if (bookItem.getType() == BookItemType.PAPER) {
				if (bookItemDTO.stock() < 0) {
					throw new InvalidBookItemFieldValueException(
							"Cập nhật tài nguyên sách thất bại. Số lượng tồn kho sách bản cứng không thể là số âm.");
				}
				
				bookItem.setStock(bookItemDTO.stock());
			}
		} catch (NullPointerException e) {
			throw new InvalidBookItemFieldValueException(
					"Cập nhật tài nguyên sách thất bại. Số lượng tồn kho sách bản cứng không được để trống.");
		
		}
		
		try {
			if (bookItem.getType() == BookItemType.AUDIO) {
				if (bookItemDTO.duration() <= 0) {
					throw new InvalidBookItemFieldValueException(
							"Cập nhật tài nguyên sách thất bại. Thời lượng tệp audio không thể bé hơn hoặc bằng 0.");
				
				}
				
				bookItem.setDuration(bookItemDTO.duration());
			}
		} catch (NullPointerException e) {
			throw new InvalidBookItemFieldValueException(
					"Cập nhật tài nguyên sách thất bại. Thời lượng tệp audio không thể để trống.");
		
		}
		
		
		if (bookItemDTO.bookId() != bookItem.getBook().getId()) {
			var bookOption = bookRepository.findById(bookItemDTO.bookId());
			
			if (bookOption.isEmpty()) {
				throw new NoBookFoundException("Cập nhật tài nguyên sách thất bại. Cuốn sách không tồn tại.");
			}
			
			bookItem.setBook(bookOption.get());
			
		}
		
		repository.save(bookItem);
		
		
	}

	public void deleteBookItemById(Integer id) {
		var bookItemOption = repository.findById(id);
		
		if (bookItemOption.isEmpty()) {
			throw new NoBookItemFoundException(id);
		}
		
		BookItem bookItem = bookItemOption.get();
		bookItem.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		bookItem.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		bookItem.setStatus(BookItemStatus.INACTIVE);
		
		repository.save(bookItem);

	}
	
}
