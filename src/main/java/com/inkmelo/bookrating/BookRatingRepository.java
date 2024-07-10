package com.inkmelo.bookrating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {
	List<BookRating> findByBookIdAndStatus(Integer bookId, BookRatingStatus status);
}
