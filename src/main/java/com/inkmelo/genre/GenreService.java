package com.inkmelo.genre;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoGenreExistException;
import com.inkmelo.exception.NoGenreFoundException;

import jakarta.validation.Valid;

@Service
public class GenreService {
	private final GenreRepository repository;
	private final GenreMappingService mappingService;
	
	public GenreService(GenreRepository repository, GenreMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public List<GenreResponseDTO> findAllGenreByStatus(GenreStatus status) {
		
		var genres = repository.findAllByStatus(status);
		
		if (genres.isEmpty()) {
			throw new NoGenreExistException("Genre data is not exist.");
		}
		
		return genres.stream()
				.map(genre -> mappingService.genreToGenreResponseDTO(genre))
				.sorted(new Comparator<GenreResponseDTO>() {
					@Override
					public int compare(GenreResponseDTO o1, GenreResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}

	public List<GenreAdminResponseDTO> findAllGenre() {
		
		var genres = repository.findAll();
		
		if (genres.isEmpty()) {
			throw new NoGenreExistException("Genre data is not exist.");
		}
		
		return genres.stream()
				.map(genre -> mappingService.genreToGenreAdminResponseDTO(genre))
				.sorted(new Comparator<GenreAdminResponseDTO>() {
					@Override
					public int compare(GenreAdminResponseDTO o1, GenreAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
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

	
}
