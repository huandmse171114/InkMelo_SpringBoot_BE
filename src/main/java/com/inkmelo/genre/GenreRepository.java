package com.inkmelo.genre;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Integer> {
	List<Genre> findAllByNameIn(Collection<String> names);
	
	List<Genre> findAllByStatus(GenreStatus status);
	
	List<Genre> findAllByStatusAndIdIn(GenreStatus status, Collection<Integer> ids);
	
	List<Genre> findAllByIdIn(Collection<Integer> ids);
}
