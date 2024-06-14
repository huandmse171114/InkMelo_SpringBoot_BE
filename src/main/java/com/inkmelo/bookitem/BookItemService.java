package com.inkmelo.bookitem;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.BookRepository;
import com.inkmelo.exception.DuplicatedBookItemException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoBookItemExistException;
import com.inkmelo.exception.NoBookItemFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookItemService {
	
	private final BookItemRepository repository;
	private final BookRepository bookRepository;
	private final BookItemMappingService mappingService;

	public List<BookItemResponseDTO> findAllBookItemByStatus(BookItemStatus status)
		throws NoBookItemExistException {
		
		var bookItems = repository.findAllByStatus(status);
		
		if (bookItems.isEmpty()) {
			throw new NoBookItemExistException("Dữ liệu về tài nguyên sách hiện đang rỗng.");
		}
		
		return bookItems.stream()
				.map(item -> mappingService.bookItemToBookItemResponseDTO(item))
				.sorted(new Comparator<BookItemResponseDTO>() {
					@Override
					public int compare(BookItemResponseDTO o1, BookItemResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}

	public List<BookItemAdminResponseDTO> findAllBookItem() 
		throws NoBookItemExistException {
		
		var bookItems = repository.findAll();
		
		if (bookItems.isEmpty()) {
			throw new NoBookItemExistException("Dữ liệu về tài nguyên sách hiện đang rỗng.");
		}
		
		return bookItems.stream()
				.map(item -> mappingService.bookItemToBookItemAdminResponseDTO(item))
				.sorted(new Comparator<BookItemAdminResponseDTO>() {
					@Override
					public int compare(BookItemAdminResponseDTO o1, BookItemAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
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
		
		repository.save(bookItem);
		
	}

	public void updateBookItem(@Valid BookItemUpdateBodyDTO bookItemDTO) 
		throws DataIntegrityViolationException {
		
		var bookItemOption = repository.findById(bookItemDTO.id());
		
		if (bookItemOption.isEmpty()) {
			throw new NoBookItemFoundException(bookItemDTO.id());
		}
		
		BookItem bookItem = bookItemOption.get();
		bookItem.setSource(bookItemDTO.source());
		bookItem.setDuration(bookItemDTO.duration());
		bookItem.setCreatedAt(Date.valueOf(LocalDate.now()));
		bookItem.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		bookItem.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		bookItem.setStatus(bookItemDTO.status());
		
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
