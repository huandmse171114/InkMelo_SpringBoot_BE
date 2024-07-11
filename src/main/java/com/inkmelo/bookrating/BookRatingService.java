package com.inkmelo.bookrating;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.book.Book;
import com.inkmelo.book.BookRepository;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.order.OrderRepository;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.customer.Customer;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookRatingService {

    private static final Logger logger = LoggerFactory.getLogger(BookRatingService.class);

    private final BookRatingRepository bookRatingRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public BookRatingService(BookRatingRepository bookRatingRepository, BookRepository bookRepository,
                             CustomerRepository customerRepository, OrderRepository orderRepository,
                             UserRepository userRepository) {
        this.bookRatingRepository = bookRatingRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<BookRatingResponseDTO> getRatingsByBook(Integer bookId) {
        return bookRatingRepository.findByBookIdAndStatus(bookId, BookRatingStatus.ACTIVE)
                .stream()
                .map(this::ratingToRatingResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookRatingResponseDTO createRating(Integer bookId, BookRatingRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("You must be logged in to create a rating");
        }

        String username = authentication.getName();
        User user = findUserByUsername(username);

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!orderRepository.existsByCustomerIdAndBookId(customer.getId(), bookId)) {
            throw new RuntimeException("You must purchase the book to rate it");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookRating rating = BookRating.builder()
                .star(request.getStar() != null ? request.getStar() : 0)  // Default to 0 if request.getStar() is null
                .comment(request.getComment() != null ? request.getComment() : "")  // Default to empty string if request.getComment() is null
                .createdAt(new Date(System.currentTimeMillis()))
                .lastUpdatedTime(new Date(System.currentTimeMillis()))
                .lastChangedBy(username)
                .status(BookRatingStatus.ACTIVE)
                .book(book)
                .customer(customer)
                .build();

        bookRatingRepository.save(rating);
        return ratingToRatingResponseDTO(rating);
    }

    @Transactional
    public BookRatingResponseDTO updateRating(Integer ratingId, BookRatingRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("You must be logged in to update a rating");
        }

        String username = authentication.getName();
        BookRating rating = bookRatingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        if (!rating.getCustomer().getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only update your own rating");
        }

        rating.setStar(request.getStar());
        rating.setComment(request.getComment());
        rating.setLastUpdatedTime(new Date(System.currentTimeMillis()));
        rating.setLastChangedBy(username);

        bookRatingRepository.save(rating);
        return ratingToRatingResponseDTO(rating);
    }

    @Transactional
    public void deleteRating(Integer ratingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("You must be logged in to delete a rating");
        }

        String username = authentication.getName();
        BookRating rating = bookRatingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        if (!rating.getCustomer().getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only delete your own rating");
        }

        rating.setStatus(BookRatingStatus.INACTIVE);
        rating.setLastUpdatedTime(new Date(System.currentTimeMillis()));
        rating.setLastChangedBy(username);

        bookRatingRepository.save(rating);
    }

    public BookRatingResponseDTO ratingToRatingResponseDTO(BookRating rating) {
        return BookRatingResponseDTO.builder()
                .star(rating.getStar())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .build();
    }

    private User findUserByUsername(String username) {
        logger.info("Looking up user by username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}