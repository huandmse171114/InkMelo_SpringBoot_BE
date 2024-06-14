package com.inkmelo.publisher;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoPublisherExistException;
import com.inkmelo.exception.NoPublisherFoundException;

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
		var publishers = repository.findAllByStatus(status);
		
		if (publishers.isEmpty()) {
			throw new NoPublisherExistException("Dữ liệu về nhà xuất bản hiện đang rỗng.");
		}
		 
		return publishers.stream()
				.map(publisher -> 
					mappingService
						.publisherToPublisherResponseDTO(publisher))
				.sorted(new Comparator<PublisherResponseDTO>() {
					@Override
					public int compare(PublisherResponseDTO o1, PublisherResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}
	
	public List<PublisherAdminResponseDTO> findAllPublisher() {
		var publishers = repository.findAll();
		
		if (publishers.isEmpty()) {
			throw new NoPublisherExistException("Dữ liệu về nhà xuất bản hiện đang rỗng.");
		}
		
		return publishers.stream()
				.map(publisher -> 
					mappingService
						.publisherToPublisherAdminResponseDTO(publisher))
				.sorted(new Comparator<PublisherAdminResponseDTO>() {
					@Override
					public int compare(PublisherAdminResponseDTO o1, PublisherAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
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
		publisher.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
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
		publisher.setLastChangedBy(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		publisher.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
		
		repository.save(publisher);
		
	}

	public Set<PublisherStatus> findAllPublisherStatus() {
		return PublisherStatus.allStatus;
	}
		
}
