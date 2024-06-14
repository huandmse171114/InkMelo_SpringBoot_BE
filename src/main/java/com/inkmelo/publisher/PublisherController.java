package com.inkmelo.publisher;

import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoPublisherExistException;
import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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


@Tag(name = "Publisher", description = "Publisher Management APIs")
@RestController
public class PublisherController {
	private PublisherService service;

	public PublisherController(PublisherService service) {
		this.service = service;
	}

	@Operation(summary = "Get All Active Publishers",
			description = "This endpoint will return all publishers that have ACTIVE status in DB | (Authority) ALL.")
	@GetMapping("/publishers")
	public List<PublisherResponseDTO> getAllActivePublisher() {
		return service.findAllPublisherByStatus(PublisherStatus.ACTIVE);
	}
	
	@Operation(summary = "Get All Publishers",
			description = "This endpoint will return all publishers in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/publishers")
	public List<PublisherAdminResponseDTO> getAllPublisher() {
		return service.findAllPublisher();
	}
	
	@Operation(summary = "Get All Publisher's Status",
			description = "This endpoint will return all publisher's status in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/publishers/status")
	public Set<PublisherStatus> getAllPublisherStatus() {
		return service.findAllPublisherStatus();
	}
	
	@Operation(summary = "Delete Publisher By Id",
			description = "This endpoint will soft delete publisher with the given id | (Authority) ADMIN, MANAGER")
	@DeleteMapping("/admin/publishers/{id}")
	public ResponseEntity<?> deletePublisherById(@PathVariable("id") Integer id){
		System.out.println(id);
		var response = new HashMap<String, Object>();
		service.deletePublisherById(id);
		response.put("message", "Xóa nhà xuất bản với mã số " + id + " thành công!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Create new Publisher",
			description = "This endpoint will create new publisher with the given information | (Authority) ADMIN, MANAGER.")
	@PostMapping("/admin/publishers")
	public ResponseEntity<?> savePublisher(@Valid @RequestBody PublisherCreateBodyDTO publisher){
		
		var response = new HashMap<String, Object>();
		service.savePublisher(publisher);
		response.put("message", "Tạo mới nhà xuất bản thành công!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.CREATED.value());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Publisher data",
			description = "This endpoint will update publisher with the given information | (Authority) ADMIN, MANAGER.")
	@PutMapping("/admin/publishers")
	public ResponseEntity<?> updatePublisher(@Valid @RequestBody PublisherUpdateBodyDTO publisher) {
		
		var response = new HashMap<String, Object>();
		service.updatePublisher(publisher);
		response.put("message", "Cập nhật nhà xuất bản thành công!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.OK.value());
		
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
	
	@ExceptionHandler(NoPublisherExistException.class)
	public ResponseEntity<?> handleNoPublisherExistException(
				NoPublisherExistException ex
			){
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
}
