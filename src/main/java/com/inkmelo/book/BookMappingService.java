package com.inkmelo.book;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inkmelo.bookrating.BookRatingService;
import com.inkmelo.bookrating.BookRatingStatus;
import com.inkmelo.genre.GenreMappingService;
import com.inkmelo.genre.GenreStatus;
import com.inkmelo.publisher.PublisherMappingService;

@Service
public class BookMappingService {
	private final PublisherMappingService publisherMappingService;
	private final BookRatingService ratingService;
	private final GenreMappingService genreMappingService;
	
	public BookMappingService(PublisherMappingService publisherMappingService, BookRatingService ratingService,
			GenreMappingService genreMappingService) {
		super();
		this.publisherMappingService = publisherMappingService;
		this.ratingService = ratingService;
		this.genreMappingService = genreMappingService;
	}

	public BookResponseDTO bookToBookResponseDTO(Book book) {
		return BookResponseDTO.builder()
				.title(book.getTitle())
				.ISBN(book.getISBN())
				.publicationDecisionNumber(book.getPublicationDecisionNumber())
				.publicationRegistConfirmNum(book.getPublicationRegistConfirmNum())
				.depositCopy(book.getDepositCopy())
				.author(book.getAuthor())
				.description(book.getDescription())
				.bookCoverImg(book.getBookCoverImg())
				.averageStar(book.getAverageStar())
				.totalRating(book.getTotalRating())
				.publisher(
						publisherMappingService
							.publisherToPublisherResponseDTO(
									book.getPublisher()))
				.ratings(book.getRatings()
						.stream()
						.filter(rating -> rating.getStatus() == BookRatingStatus.ACTIVE)
						.map(rating -> ratingService.ratingToRatingResponseDTO(rating))
						.collect(Collectors.toList()))
				.genres(book.getGenres()
						.stream()
						.filter(genre -> genre.getStatus() == GenreStatus.ACTIVE)
						.map(genre -> genreMappingService.genreToGenreResponseDTO(genre))
						.collect(Collectors.toList()))
				.build();
	}

	public BookAdminResponseDTO bookToBookAdminResponseDTO(Book book) {
		return BookAdminResponseDTO.builder()
				.title(book.getTitle())
				.ISBN(book.getISBN())
				.publicationDecisionNumber(book.getPublicationDecisionNumber())
				.publicationRegistConfirmNum(book.getPublicationRegistConfirmNum())
				.depositCopy(book.getDepositCopy())
				.author(book.getAuthor())
				.description(book.getDescription())
				.bookCoverImg(book.getBookCoverImg())
				.averageStar(book.getAverageStar())
				.totalRating(book.getTotalRating())
				.publisher(
						publisherMappingService
							.publisherToPublisherResponseDTO(
									book.getPublisher()))
				.ratings(book.getRatings()
						.stream()
						.map(rating -> ratingService.ratingToRatingResponseDTO(rating))
						.collect(Collectors.toList()))
				.genres(book.getGenres()
						.stream()
						.map(genre -> genreMappingService.genreToGenreResponseDTO(genre))
						.collect(Collectors.toList()))
				.createdAt(book.getCreatedAt())
				.lastChangedBy(book.getLastChangedBy())
				.lastUpdatedTime(book.getLastUpdatedTime())
				.status(book.getStatus())
				.build();
	}
}
