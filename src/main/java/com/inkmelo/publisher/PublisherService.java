package com.inkmelo.publisher;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoPublisherExistException;
import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
	private final PublisherRepository repository;
	private final PublisherMappingService mappingService;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;

	public ResponseEntity<?> findAllPublisherByStatus(PublisherStatus status, Integer page, Integer size, String keyword) {
		
//		Get publishers, no paging
		if (page == null & size == null) {
			var publishers = repository.findAllByStatusAndNameContainingIgnoreCase(status, keyword);
			
			if (publishers.isEmpty()) {
				throw new NoPublisherExistException("Dữ liệu về nhà xuất bản hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(publishers.stream()
					.map(publisher -> 
					mappingService
					.publisherToPublisherResponseDTO(publisher))
					.sorted(new Comparator<PublisherResponseDTO>() {
						@Override
						public int compare(PublisherResponseDTO o1, PublisherResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
//		Get publishers, with paging
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			var pagePublishers = repository.findAllByStatusAndNameContainingIgnoreCase(status, keyword, paging);
			
			return getPublisherResponseDTO(pagePublishers);
			
		}
		
	}
	
	public ResponseEntity<?> findAllPublisher(Integer page, Integer size, String keyword) {
		
//		Get publishers, no paging
		if (page == null & size == null) {
			var publishers = repository.findAllByNameContainingIgnoreCase(keyword);
			
			if (publishers.isEmpty()) {
				throw new NoPublisherExistException("Dữ liệu về nhà xuất bản hiện đang rỗng.");
			}
			
			return new ResponseEntity<>(publishers.stream()
					.map(publisher -> 
						mappingService
							.publisherToPublisherAdminResponseDTO(publisher))
					.sorted(new Comparator<PublisherAdminResponseDTO>() {
						@Override
						public int compare(PublisherAdminResponseDTO o1, PublisherAdminResponseDTO o2) {
							return o1.id().compareTo(o2.id());
						}
					})
					.toList(), HttpStatus.OK);
			
//		Get publishers, with paging
		}else {
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			var pagePublishers = repository.findAllByNameContainingIgnoreCase(keyword, paging);
			
			return getPublisherAdminResponseDTO(pagePublishers);
		}
		
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
	
	private ResponseEntity<?> getPublisherResponseDTO(Page<Publisher> pagePublishers) {
		var publishers = pagePublishers.getContent();
		
		if (publishers.isEmpty()) {
			throw new NoPublisherExistException("Dữ liệu về nhà xuất bản hiện đang rỗng.");
		}
		
		var response = publishers.stream()
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
		
		return Utils.generatePagingListResponseEntity(
				pagePublishers.getTotalElements(), 
				response, 
				pagePublishers.getTotalPages(), 
				pagePublishers.getNumber(), 
				HttpStatus.OK);
	}
	
	private ResponseEntity<?> getPublisherAdminResponseDTO(Page<Publisher> pagePublishers) {
		var publishers = pagePublishers.getContent();
		
		if (publishers.isEmpty()) {
			throw new NoPublisherExistException("Dữ liệu về nhà xuất bản hiện đang rỗng.");
		}
		
		var response = publishers.stream()
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
		
		return Utils.generatePagingListResponseEntity(
				pagePublishers.getTotalElements(), 
				response, 
				pagePublishers.getTotalPages(), 
				pagePublishers.getNumber(), 
				HttpStatus.OK);
	}
		
}
