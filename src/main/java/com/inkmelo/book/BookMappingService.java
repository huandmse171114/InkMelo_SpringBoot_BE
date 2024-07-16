package com.inkmelo.book;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.bookrating.BookRating;
import com.inkmelo.bookrating.BookRatingService;
import com.inkmelo.bookrating.BookRatingStatus;
import com.inkmelo.exception.NoGenreFoundException;
import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.genre.Genre;
import com.inkmelo.genre.GenreMappingService;
import com.inkmelo.genre.GenreRepository;
import com.inkmelo.genre.GenreStatus;
import com.inkmelo.publisher.Publisher;
import com.inkmelo.publisher.PublisherMappingService;
import com.inkmelo.publisher.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMappingService {
    private final PublisherMappingService publisherMappingService;
    private final BookRatingService ratingService;
    private final GenreMappingService genreMappingService;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    public BookResponseDTO bookToBookResponseDTO(Book book) {
        List<BookRating> activeRatings = book.getRatings().stream()
            .filter(rating -> rating.getStatus() == BookRatingStatus.ACTIVE)
            .collect(Collectors.toList());

        float averageStar = calculateAverageStar(activeRatings);
        int totalRating = activeRatings.size();

        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .ISBN(book.getISBN())
                .publicationDecisionNumber(book.getPublicationDecisionNumber())
                .publicationRegistConfirmNum(book.getPublicationRegistConfirmNum())
                .depositCopy(book.getDepositCopy())
                .author(book.getAuthor())
                .description(book.getDescription())
                .bookCoverImg(book.getBookCoverImg())
                .averageStar(averageStar)
                .totalRating(totalRating)
                .publisher(publisherMappingService.publisherToPublisherResponseDTO(book.getPublisher()))
                .ratings(activeRatings.stream()
                        .map(rating -> ratingService.ratingToRatingResponseDTO(rating))
                        .collect(Collectors.toList()))
                .genres(book.getGenres().stream()
                        .filter(genre -> genre.getStatus() == GenreStatus.ACTIVE)
                        .map(genre -> genreMappingService.genreToGenreResponseDTO(genre))
                        .collect(Collectors.toList()))
                .status(book.getStatus())
                .build();
    }
    
    public BookOrderResponseDTO bookToBookOrderResponseDTO(Book book) {
        return BookOrderResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .bookCoverImg(book.getBookCoverImg())
                .publisherName(book.getPublisher().getName())
                .genres(book.getGenres().stream()
                        .filter(genre -> genre.getStatus() == GenreStatus.ACTIVE)
                        .map(genre -> genreMappingService.genreToGenreResponseDTO(genre))
                        .collect(Collectors.toList()))
                .build();
    }

    private float calculateAverageStar(List<BookRating> ratings) {
        if (ratings.isEmpty()) {
            return 0;
        }

        int totalStars = ratings.stream().mapToInt(BookRating::getStar).sum();
        return (float) totalStars / ratings.size();
    }

    public BookAdminResponseDTO bookToBookAdminResponseDTO(Book book) {
        List<BookRating> activeRatings = book.getRatings().stream()
            .filter(rating -> rating.getStatus() == BookRatingStatus.ACTIVE)
            .collect(Collectors.toList());

        float averageStar = calculateAverageStar(activeRatings);
        int totalRating = activeRatings.size();

        return BookAdminResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .ISBN(book.getISBN())
                .publicationDecisionNumber(book.getPublicationDecisionNumber())
                .publicationRegistConfirmNum(book.getPublicationRegistConfirmNum())
                .depositCopy(book.getDepositCopy())
                .author(book.getAuthor())
                .description(book.getDescription())
                .bookCoverImg(book.getBookCoverImg())
                .averageStar(averageStar)
                .totalRating(totalRating)
                .publisher(publisherMappingService.publisherToPublisherResponseDTO(book.getPublisher()))
                .ratings(activeRatings.stream()
                        .map(rating -> ratingService.ratingToRatingResponseDTO(rating))
                        .collect(Collectors.toList()))
                .genres(book.getGenres().stream()
                        .map(genre -> genreMappingService.genreToGenreResponseDTO(genre))
                        .collect(Collectors.toList()))
                .createdAt(book.getCreatedAt())
                .lastChangedBy(book.getLastChangedBy())
                .lastUpdatedTime(book.getLastUpdatedTime())
                .status(book.getStatus())
                .build();
    }

    public Book bookCreateBodyDTOToBook(BookCreateBodyDTO bookDTO) 
            throws NoGenreFoundException, NoPublisherFoundException {
        List<Genre> genres = genreRepository.findAllByStatusAndIdIn(GenreStatus.ACTIVE, bookDTO.genreIds());

        if (genres.isEmpty()) {
            throw new NoGenreFoundException("Tạo mới sách thất bại. Thể loại sách không tồn tại.");
        }

        Optional<Publisher> publisherOption = publisherRepository.findById(bookDTO.publisherId());

        if (publisherOption.isEmpty()) {
            throw new NoPublisherFoundException("Tạo mới sách thất bại. Nhà xuất bản không tồn tại.");
        }

        Publisher publisher = publisherOption.get();

        return Book.builder()
                .title(bookDTO.title())
                .ISBN(bookDTO.ISBN())
                .publicationDecisionNumber(bookDTO.publicationDecisionNumber())
                .publicationRegistConfirmNum(bookDTO.publicationRegistConfirmNum())
                .depositCopy(bookDTO.depositCopy())
                .author(bookDTO.author())
                .description(bookDTO.description())
                .bookCoverImg(bookDTO.bookCoverImg())
                .genres(genres)
                .publisher(publisher)
                .averageStar(0)
                .totalRating(0)
                .createdAt(Date.valueOf(LocalDate.now()))
                .lastChangedBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .lastUpdatedTime(Date.valueOf(LocalDate.now()))
                .status(BookStatus.ACTIVE)
                .build();
    }
}