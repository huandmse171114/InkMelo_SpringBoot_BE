package com.inkmelo.publisher;

import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.utils.Utils;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class PublisherController {
	private PublisherService service;

	public PublisherController(PublisherService service) {
		this.service = service;
	}
	
	@GetMapping("/publishers")
	public List<PublisherResponseDTO> getAllActivePublisher() {
		return service.findAllPublisherByStatus(PublisherStatus.ACTIVE);
	}
	
	@GetMapping("/admin/publishers")
	public List<PublisherAdminResponseDTO> getAllPublisher() {
		return service.findAllPublisher();
	}
	
	@DeleteMapping("/publishers/{id}")
	public ResponseEntity<?> deletePublisherById(@PathVariable("id") Integer id){
		
		var response = new HashMap<String, String>();
		service.deletePublisherById(id);
		response.put("message", "Delete publisher with id " + id + " successfully!");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/publishers")
	public ResponseEntity<?> savePublisher(@Valid @RequestBody PublisherCreateBodyDTO publisher){
		
		var response = new HashMap<String, String>();
		service.savePublisher(publisher);
		response.put("message", "Create new publisher successfully!");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/publishers")
	public ResponseEntity<?> updatePublisher(@Valid @RequestBody PublisherUpdateBodyDTO publisher) {
		
		var response = new HashMap<String, String>();
		service.updatePublisher(publisher);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex
			) {
		var errors = new HashMap<String, Object>();
		
		errors.put("timestamp", Utils.getCurrentTimestamp());
		errors.put("status", HttpStatus.BAD_REQUEST.value());
		
		ex.getBindingResult().getAllErrors()
			.forEach(e -> {
				var fieldName = ((FieldError) e).getField();
				var errorMsg = e.getDefaultMessage();
				errors.put(fieldName, errorMsg);
			});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex
			) {
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoPublisherFoundException.class)
	public ResponseEntity<?> handleNoPublisherFoundException(
				NoPublisherFoundException ex
			){
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
}
