package com.inkmelo.publisher;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PublisherMappingService {
	
	public PublisherResponseDTO publisherToPublisherResponseDTO(Publisher publisher) {
		return PublisherResponseDTO.builder()
				.id(publisher.getId())
				.name(publisher.getName())
				.description(publisher.getDescription())
				.logoImg(publisher.getLogoImg())
				.build();
	}
	
	public PublisherAdminResponseDTO publisherToPublisherAdminResponseDTO(Publisher publisher) {
		return PublisherAdminResponseDTO.builder()
				.id(publisher.getId())
				.name(publisher.getName())
				.description(publisher.getDescription())
				.logoImg(publisher.getLogoImg())
				.createdAt(publisher.getCreatedAt())
				.lastChangedBy(publisher.getLastChangedBy())
				.lastUpdatedTime(publisher.getLastUpdatedTime())
				.status(publisher.getStatus())
				.build();
	}
	
	public Publisher publisherCreateBodyDTOToPublisher(PublisherCreateBodyDTO publisher) {
		return Publisher.builder()
					.name(publisher.name())
					.description(publisher.description())
					.logoImg(publisher.logoImg())
					.createdAt(Date.valueOf(LocalDate.now()))
					.lastChangedBy(SecurityContextHolder.getContext()
							.getAuthentication().getName())
					.lastUpdatedTime(Date.valueOf(LocalDate.now()))
					.status(PublisherStatus.ACTIVE)
					.build();
	}
	
}
