package com.inkmelo.genre;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
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

import com.inkmelo.exception.NoGenreExistException;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.utils.Utils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {
	private final GenreRepository repository;
	private final GenreMappingService mappingService;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 8;
	
	public ResponseEntity<?> findAllGenreByStatus(GenreStatus status, Integer page, Integer size, String keyword) {

//		Get genres, no paging
		if (page == null & size == null) {
			var genres = repository.findAllByStatusAndNameContainingIgnoreCaseOrderByIdAsc(status, keyword);
			
			if (genres.isEmpty()) {
				throw new NoGenreExistException("Dữ liệu về thể loại sách hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(genres.stream()
					.map(genre -> mappingService.genreToGenreResponseDTO(genre))
					.sorted(new Comparator<GenreResponseDTO>() {
						@Override
						public int compare(GenreResponseDTO o1, GenreResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
			
//		Get genres, with paging
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size); 
			
			var pageGenres = repository.findAllByStatusAndNameContainingIgnoreCaseOrderByIdAsc(status, keyword, paging);
			
			return getGenreResponseDTO(pageGenres);
		}
	
	}

	public ResponseEntity<?> findAllGenre(Integer page, Integer size, String keyword) {

//		Get genres, no paging
		if (page == null & size == null) {
				
			var genres = repository.findAllByNameContainingIgnoreCaseOrderByIdAsc(keyword);
			
			if (genres.isEmpty()) {
				throw new NoGenreExistException("Dữ liệu về thể loại sách hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(genres.stream()
					.map(genre -> mappingService.genreToGenreAdminResponseDTO(genre))
					.sorted(new Comparator<GenreAdminResponseDTO>() {
						@Override
						public int compare(GenreAdminResponseDTO o1, GenreAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
			
//		Get genres, with paging
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size); 
			
			var pageGenres = repository.findAllByNameContainingIgnoreCaseOrderByIdAsc(keyword, paging);
			
			return getGenreAdminResponseDTO(pageGenres);
			
		}
	}


	public Set<GenreStatus> findAllGenreStatus() {
		return GenreStatus.allStatus;
	}

	public void saveGenre(@Valid GenreCreateBodyDTO genreDTO) throws DataIntegrityViolationException {
		Genre genre = mappingService.genreCreateBodyDTOToGenre(genreDTO);
		
		repository.save(genre);
	}

	public void updateGenre(GenreUpdateBodyDTO genreDTO) throws DataIntegrityViolationException {
		var genreOption = repository.findById(genreDTO.id());
		
		if (genreOption.isEmpty()) {
			throw new NoGenreFoundException(genreDTO.id());
		}
		
		Genre genre = genreOption.get();
		genre.setName(genreDTO.name());
		genre.setDescription(genreDTO.description());
		genre.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		genre.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		genre.setStatus(genreDTO.status());
		
		repository.save(genre);
	}
	
	public void deleteGenreById(Integer id) {
		var genreOption = repository.findById(id);
		
		if (genreOption.isEmpty()) {
			throw new NoGenreFoundException(id);
		}
		
		Genre genre = genreOption.get();
		genre.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		genre.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		genre.setStatus(GenreStatus.INACTIVE);
		
		repository.save(genre);
	}

	
	private ResponseEntity<?> getGenreResponseDTO(Page<Genre> pageGenres) {

		var genres = pageGenres.getContent();
		
		if (genres.isEmpty()) {
			throw new NoGenreExistException("Dữ liệu về thể loại sách hiện đang rỗng.");
		}
		
		var response = genres.stream()
				.map(genre -> mappingService.genreToGenreResponseDTO(genre))
				.sorted(new Comparator<GenreResponseDTO>() {
					@Override
					public int compare(GenreResponseDTO o1, GenreResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
		
		return Utils.generatePagingListResponseEntity(
				pageGenres.getTotalElements(), 
				response, 
				pageGenres.getTotalPages(), 
				pageGenres.getNumber(), 
				HttpStatus.OK);
	}
	
	private ResponseEntity<?> getGenreAdminResponseDTO(Page<Genre> pageGenres) {

		var genres = pageGenres.getContent();
		
		if (genres.isEmpty()) {
			throw new NoGenreExistException("Dữ liệu về thể loại sách hiện đang rỗng.");
		}
		
		var response = genres.stream()
				.map(genre -> mappingService.genreToGenreAdminResponseDTO(genre))
				.sorted(new Comparator<GenreAdminResponseDTO>() {
					@Override
					public int compare(GenreAdminResponseDTO o1, GenreAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
		
		return Utils.generatePagingListResponseEntity(
				pageGenres.getTotalElements(), 
				response, 
				pageGenres.getTotalPages(), 
				pageGenres.getNumber(), 
				HttpStatus.OK);
	}
	
    public Genre findById(Integer genreId) {
        return repository.getById(genreId);
    }
}
