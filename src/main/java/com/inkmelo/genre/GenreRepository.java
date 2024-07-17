package com.inkmelo.genre;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Integer> {
	List<Genre> findAllByNameInOrderByIdAsc(Collection<String> names);
	
	List<Genre> findAllByNameContainingIgnoreCaseOrderByIdAsc(String name);
	
	List<Genre> findAllByStatusOrderByIdAsc(GenreStatus status);
	
	List<Genre> findAllByStatusAndNameContainingIgnoreCaseOrderByIdAsc(GenreStatus status, String name);
	
	Page<Genre> findAllByStatusOrderByIdAsc(GenreStatus status, Pageable pageable);
	
	Page<Genre> findAllByOrderByIdAsc(Pageable pageable);
	
	Page<Genre> findAllByNameContainingIgnoreCaseOrderByIdAsc(String name, Pageable pageable);
	
	Page<Genre> findAllByStatusAndNameContainingIgnoreCaseOrderByIdAsc(GenreStatus status, String name, Pageable pageable);
	
	List<Genre> findAllByStatusAndIdInOrderByIdAsc(GenreStatus status, Collection<Integer> ids);
	
	List<Genre> findAllByIdInOrderByIdAsc(Collection<Integer> ids);
}
