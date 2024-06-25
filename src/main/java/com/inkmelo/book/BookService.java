package com.inkmelo.book;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.category.CategoryAdminResponseDTO;
import com.inkmelo.category.CategoryStatus;
import com.inkmelo.exception.NoBookExistException;
import com.inkmelo.exception.NoBookFoundException;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.genre.GenreRepository;
import com.inkmelo.genre.GenreStatus;
import com.inkmelo.publisher.PublisherRepository;
import com.inkmelo.utils.Utils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository repository;
	private final BookMappingService mappingService;
	private final GenreRepository genreRepository;
	private final PublisherRepository publisherRepository;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;

	public ResponseEntity<?> findAllBookByStatus(BookStatus status, Integer page, Integer size, String keyword) 
			throws NoBookExistException{
		
//		Get books, without paging
		if (page == null & size == null) {
			var books = repository
					.findAllByStatusAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
							status, 
							keyword, 
							keyword);
			
			if (books.isEmpty()) {
				throw new NoBookExistException("Dữ liệu về sách hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(books.stream()
					.map(book -> mappingService.bookToBookResponseDTO(book))
					.sorted(new Comparator<BookResponseDTO>() {
						@Override
						public int compare(BookResponseDTO o1, BookResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
			
//		Get books , with paging
		}else {
			
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			Page<Book> pageBooks = repository
					.findAllByStatusAndTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
							status, 
							keyword, 
							keyword,
							paging);
			
			var books = pageBooks.getContent();
			
			if (books.isEmpty()) {
				throw new NoBookExistException("Dữ liệu về sách hiện đang rỗng.");
			}
			
			var response = books.stream()
					.map(book -> mappingService.bookToBookResponseDTO(book))
					.sorted(new Comparator<BookResponseDTO>() {
						@Override
						public int compare(BookResponseDTO o1, BookResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList();
			
			return Utils.generatePagingListResponseEntity(
					pageBooks.getTotalElements(),
					response, 
					pageBooks.getTotalPages(), 
					pageBooks.getNumber(), 
					HttpStatus.OK);
			
		}
		
	}
	
	public ResponseEntity<?> findAllBook(Integer page, Integer size, String keyword) 
			throws NoBookExistException{
		
//		Get books, without paging
		if (page == null & size == null) {
			var books = repository
					.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
			
			if (books.isEmpty()) {
				throw new NoBookExistException("Dữ liệu về sách hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(books.stream()
					.map(book -> mappingService.bookToBookAdminResponseDTO(book))
					.sorted(new Comparator<BookAdminResponseDTO>() {
						@Override
						public int compare(BookAdminResponseDTO o1, BookAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
//		Get books, with paging
		}else {
			
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			var pageBooks = repository
					.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword, paging);
			
			var books = pageBooks.getContent();
			
			if (books.isEmpty()) {
				throw new NoBookExistException("Dữ liệu về sách hiện đang rỗng.");
			}
			
			var response = books.stream()
					.map(book -> mappingService.bookToBookAdminResponseDTO(book))
					.sorted(new Comparator<BookAdminResponseDTO>() {
						@Override
						public int compare(BookAdminResponseDTO o1, BookAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList();
			
			return Utils.generatePagingListResponseEntity(
					pageBooks.getTotalElements(), 
					response, 
					pageBooks.getTotalPages(), 
					pageBooks.getNumber(), 
					HttpStatus.OK);
			
		}
		
	}
	
	public List<BookResponseDTO> searchBook(String keyword) 
			throws NoBookFoundException{
		Stream<BookResponseDTO> booksByTitle = repository.findAllByTitleContainingIgnoreCase(keyword)
				.stream()
				.map(book -> mappingService.bookToBookResponseDTO(book));
		
		Stream<BookResponseDTO> booksByAuthor = repository.findAllByAuthorContainingIgnoreCase(keyword)
				.stream()
				.map(book -> mappingService.bookToBookResponseDTO(book));
				
		if (booksByAuthor.count() == 0 & booksByTitle.count() == 0) {
			throw new NoBookFoundException("Không có cuốn sấch nào phù hợp với từ khóa " + keyword);
		}
		
		return Stream.concat(booksByTitle, booksByAuthor).toList();

    }

	public void saveBook(BookCreateBodyDTO bookDTO) 
			throws NoPublisherFoundException, NoGenreFoundException, 
			DataIntegrityViolationException {
		Book book = mappingService.bookCreateBodyDTOToBook(bookDTO);
		
		repository.save(book);
	}

	public void updateBook(BookUpdateBodyDTO bookDTO) 
			throws NoBookFoundException, NoGenreFoundException,
				NoPublisherFoundException, DataIntegrityViolationException {
		var bookOption = repository.findById(bookDTO.id());
		
		if  (bookOption.isEmpty()) {
			throw new NoBookFoundException(bookDTO.id());
		}
		
		Book book = bookOption.get();
		book.setTitle(bookDTO.title());
		book.setISBN(bookDTO.ISBN());
		book.setPublicationDecisionNumber(bookDTO.publicationDecisionNumber());
		book.setPublicationRegistConfirmNum(bookDTO.publicationRegistConfirmNum());
		book.setDepositCopy(bookDTO.depositCopy());
		book.setAuthor(bookDTO.author());
		book.setDescription(bookDTO.description());
		book.setBookCoverImg(bookDTO.bookCoverImg());
		book.setStatus(bookDTO.status());
		book.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		book.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		
		var oldGenreIds = book.getGenres()
				.stream()
				.map(genre -> genre.getId())
				.toList();
		
		if (!oldGenreIds.containsAll(bookDTO.genreIds()) |
				!bookDTO.genreIds().containsAll(oldGenreIds)) {
			var newGenres = genreRepository.findAllByIdIn(bookDTO.genreIds());
			if (newGenres.size() < bookDTO.genreIds().size()) {
				throw new NoGenreFoundException("Cập nhật sách thất bại. Một số thể loại sách không tồn tại.");
			}
			
			book.setGenres(newGenres);
		}
		
		if (book.getPublisher().getId() != bookDTO.id()) {
			var newPublisher = publisherRepository.findById(bookDTO.publisherId());
			
			if (newPublisher.isEmpty()) {
				throw new NoPublisherFoundException("Cập nhật sách thất bại. Nhà xuất bản sách không tồn tại");
			}
			
			book.setPublisher(newPublisher.get());
		}
		
		repository.save(book);
		
		
	}

	public void deleteBookById(Integer id) 
		throws NoBookFoundException {
		var bookOption = repository.findById(id);
		
		if  (bookOption.isEmpty()) {
			throw new NoBookFoundException(id);
		}
		
		Book book = bookOption.get();
		book.setStatus(BookStatus.INACTIVE);
		book.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		book.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		
		repository.save(book);
	}

	public Set<BookStatus> findAllBookStatus() {
		return BookStatus.allStatus;
	}

	public BookResponseDTO findBookById(Integer id) {
		Optional<Book> book = repository.findById(id);
		
		if (book.isEmpty()) {
			throw new NoBookFoundException(id);
		}
		
		return mappingService.bookToBookResponseDTO(book.get());
	}
	
	public BookAdminResponseDTO findAdminBookById(Integer id) {
		Optional<Book> book = repository.findById(id);
		
		if (book.isEmpty()) {
			throw new NoBookFoundException(id);
		}
		
		return mappingService.bookToBookAdminResponseDTO(book.get());
	}
	
}

