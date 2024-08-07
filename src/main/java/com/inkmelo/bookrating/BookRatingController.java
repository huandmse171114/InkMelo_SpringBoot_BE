package com.inkmelo.bookrating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.utils.Utils;


import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book Rating", description = "Book Rating Management APIs")
@RestController
public class BookRatingController {

    private final BookRatingService bookRatingService;

    @Autowired
    public BookRatingController(BookRatingService bookRatingService) {
        this.bookRatingService = bookRatingService;
    }

    @GetMapping("store/api/v1/ratings/book/{bookId}")
    public ResponseEntity<List<BookRatingResponseDTO>> getRatingsByBook(@PathVariable Integer bookId) {
        List<BookRatingResponseDTO> ratings = bookRatingService.getRatingsByBook(bookId);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping("store/api/v1/ratings/book/{bookId}")
    public ResponseEntity<?> createRating(@PathVariable Integer bookId, @RequestBody BookRatingRequestDTO request) {
        try {
            BookRatingResponseDTO response = bookRatingService.createRating(bookId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("store/api/v1/ratings/{ratingId}")
    public BookRatingResponseDTO updateRating(@PathVariable Integer ratingId, @RequestBody BookRatingRequestDTO request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookRatingService.updateRating(ratingId, request);
    }

    @PutMapping("store/api/v1/ratings/delete/{ratingId}")
    public void deleteRating(@PathVariable Integer ratingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bookRatingService.deleteRating(ratingId);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}