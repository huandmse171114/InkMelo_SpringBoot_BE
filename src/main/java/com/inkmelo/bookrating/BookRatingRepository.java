package com.inkmelo.bookrating;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {
    List<BookRating> findByBookIdAndStatus(Integer bookId, BookRatingStatus status);

    @Query("SELECT new com.inkmelo.bookrating.BookRatingStatsDTO(br.book.id, AVG(br.star), COUNT(br.comment)) " +
           "FROM BookRating br " +
           "WHERE br.status = :status " + // Added a space before "GROUP BY"
           "GROUP BY br.book.id")
    List<BookRatingStatsDTO> findBookRatingStats(@Param("status") BookRatingStatus status);
    
    Optional<BookRating> findByCustomerIdAndBookIdAndStatus(Integer customerId, Integer bookId, BookRatingStatus status);
    
    Optional<BookRating> findByCustomerIdAndBookIdAndOrderId(Integer customerId, Integer bookId, Integer orderId);
    
//    Optional<BookRating> findByCustomerIdAndBookIdAndOrderId(Integer customerId, Integer bookId, Integer orderId, BookRatingStatus status);
}