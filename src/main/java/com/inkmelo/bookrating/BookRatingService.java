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
import com.inkmelo.order.Order;
import com.inkmelo.order.OrderRepository;
import com.inkmelo.order.OrderStatus;
import com.inkmelo.orderdetail.OrderDetail;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerRatingResponseDTO;

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
            throw new RuntimeException("Vui lòng đăng nhập để đánh giá sản phẩm!");
        }

        String username = authentication.getName();
        User user = findUserByUsername(username);

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng!"));

        // Check if the order exists and is completed
        Order order = orderRepository.findByIdAndCustomerIdAndStatus(request.getOrderId(), customer.getId(), OrderStatus.PAYMENT_FINISHED)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không hợp lệ hoặc chưa hoàn thành thanh toán!"));

        boolean hasPurchasedBook = order.getOrderDetails().stream()
                .anyMatch(orderDetail -> orderDetail.getBookPackage().getBook().getId().equals(bookId));

        if (!hasPurchasedBook) {
            throw new RuntimeException("Vui lòng mua cuốn sách này để có thể viết đánh giá!");
        }

        // Check for existing rating for the same order
        if (bookRatingRepository.findByCustomerIdAndBookIdAndOrderId(customer.getId(), bookId, request.getOrderId()).isPresent()) {
            throw new RuntimeException("Không thể thực hiện đánh giá! Bạn đã đánh giá sản phẩm này trong đơn hàng này rồi.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm!"));

        BookRating rating = BookRating.builder()
                .star(request.getStar() != null ? request.getStar() : 0)
                .comment(request.getComment() != null ? request.getComment() : "")
                .createdAt(new Date(System.currentTimeMillis()))
                .lastUpdatedTime(new Date(System.currentTimeMillis()))
                .lastChangedBy(username)
                .status(BookRatingStatus.ACTIVE)
                .book(book)
                .customer(customer)
                .order(order)  // Set the order
                .build();

        bookRatingRepository.save(rating);
        return ratingToRatingResponseDTO(rating);
    }


    @Transactional
    public BookRatingResponseDTO updateRating(Integer ratingId, BookRatingRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("Vui lòng đăng nhập để đánh giá sản phẩm!");
        }

        String username = authentication.getName();
        BookRating rating = bookRatingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Không tim thấy đánh giá!"));

        if (!rating.getCustomer().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Bạn chỉ được phép cập nhật đánh giá của chính mình!");
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
            throw new RuntimeException("Vui lòng đăng nhập để đánh giá sản phẩm!");
        }

        String username = authentication.getName();
        BookRating rating = bookRatingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Không tim thấy đánh giá!"));

        if (!rating.getCustomer().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Bạn chỉ được phép xóa đánh giá của chính mình!");
        }

        rating.setStatus(BookRatingStatus.INACTIVE);
        rating.setLastUpdatedTime(new Date(System.currentTimeMillis()));
        rating.setLastChangedBy(username);

        bookRatingRepository.save(rating);
    }

    public BookRatingResponseDTO ratingToRatingResponseDTO(BookRating rating) {
    	CustomerRatingResponseDTO customerDTO = CustomerRatingResponseDTO.builder()
                .fullname(rating.getCustomer().getFullname())
                .build();
    	return BookRatingResponseDTO.builder()
                .star(rating.getStar())
                .comment(rating.getComment())
                .customer(customerDTO)
                .createdAt(rating.getCreatedAt())
                .build();
    }

    private User findUserByUsername(String username) {
        logger.info("Looking up user by username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
    }
}