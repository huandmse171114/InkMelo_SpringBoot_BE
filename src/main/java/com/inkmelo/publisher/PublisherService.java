package com.inkmelo.publisher;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.exc.InvalidNullException;
import com.inkmelo.exception.NoPublisherFoundException;

import jakarta.validation.Valid;

@Service
public class PublisherService {
	private final PublisherRepository repository;
	private final PublisherMappingService mappingService;
	
	public PublisherService(PublisherRepository repository, PublisherMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public List<PublisherResponseDTO> findAllPublisherByStatus(PublisherStatus status) {
		return repository.findAllByStatus(status)
				.stream()
				.map(publisher -> 
					mappingService
						.publisherToPublisherResponseDTO(publisher))
				.toList();
	}
	
	public List<PublisherAdminResponseDTO> findAllPublisher() {
		return repository.findAll()
				.stream()
				.map(publisher -> 
					mappingService
						.publisherToPublisherAdminResponseDTO(publisher))
				.toList();
	}

	public void savePublisher(PublisherCreateBodyDTO publisher) throws DataIntegrityViolationException {
		Publisher newPublisher = mappingService.publisherCreateBodyDTOToPublisher(publisher);
		
		repository.save(newPublisher);
		
	}
	
	public void updatePublisher(PublisherUpdateBodyDTO publisherDTO) throws DataIntegrityViolationException {
		
		var publisherOption = repository.findById(publisherDTO.id());
		
		if (publisherOption.isEmpty()) {
			throw new NoPublisherFoundException(publisherDTO.id());
		}
		
		Publisher publisher = publisherOption.get();
		publisher.setName(publisherDTO.name());
		publisher.setDescription(publisherDTO.description());
		publisher.setLogoImg(publisherDTO.logoImg());
		publisher.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		publisher.setLastChangedBy("HuanDM");
		publisher.setStatus(publisherDTO.status());
		
		repository.save(publisher);
		
	}

	public void deletePublisherById(Integer id) throws NoPublisherFoundException {
		
		var publisherOption = repository.findById(id);
		
		if (publisherOption.isEmpty()) {
			throw new NoPublisherFoundException(id);
		}
		
		Publisher publisher = publisherOption.get();
		publisher.setStatus(PublisherStatus.INACTIVE);
		repository.save(publisher);
		
	}
		
}
