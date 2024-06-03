package com.inkmelo.publisher;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class PublisherMappingService {
	
	public PublisherResponseDTO publisherToPublisherResponseDTO(Publisher publisher) {
		return PublisherResponseDTO.builder()
				.name(publisher.getName())
				.description(publisher.getDescription())
				.logoImg(publisher.getLogoImg())
				.build();
	}
	
	public PublisherAdminResponseDTO publisherToPublisherAdminResponseDTO(Publisher publisher) {
		return PublisherAdminResponseDTO.builder()
				.name(publisher.getName())
				.description(publisher.getDescription())
				.logoImg(publisher.getLogoImg())
				.createdAt(publisher.getCreatedAt())
				.lastChangedBy(publisher.getLastChangedBy())
				.lastUpdatedTime(publisher.getLastUpdatedTime())
				.build();
	}
	
	public Publisher publisherCreateBodyDTOToPublisher(PublisherCreateBodyDTO publisher) {
		return Publisher.builder()
					.name(publisher.name())
					.description(publisher.description())
					.logoImg(publisher.logoImg())
					.createdAt(Date.valueOf(LocalDate.now()))
					.lastChangedBy("HuanDM") //Change to sender username after login function finished
					.lastUpdatedTime(Date.valueOf(LocalDate.now()))
					.status(PublisherStatus.ACTIVE)
					.build();
	}
	
}
