package com.inkmelo.genre;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Integer> {
	List<Genre> findAllByNameIn(Collection<String> names);
	
	List<Genre> findAllByNameContainingIgnoreCase(String name);
	
	List<Genre> findAllByStatus(GenreStatus status);
	
	List<Genre> findAllByStatusAndNameContainingIgnoreCase(GenreStatus status, String name);
	
	Page<Genre> findAllByStatus(GenreStatus status, Pageable pageable);
	
	Page<Genre> findAll(Pageable pageable);
	
	Page<Genre> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
	
	Page<Genre> findAllByStatusAndNameContainingIgnoreCase(GenreStatus status, String name, Pageable pageable);
	
	List<Genre> findAllByStatusAndIdIn(GenreStatus status, Collection<Integer> ids);
	
	List<Genre> findAllByIdIn(Collection<Integer> ids);
}
