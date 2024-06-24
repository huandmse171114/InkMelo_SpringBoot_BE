package com.inkmelo.publisher;

import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoPublisherExistException;
import com.inkmelo.exception.NoPublisherFoundException;
import com.inkmelo.utils.MessageResponseDTO;
import com.inkmelo.utils.PagingListResposneDTO;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.ToString;

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
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Publisher", description = "Publisher Management APIs")
@RestController
public class PublisherController {
	private PublisherService service;

	public PublisherController(PublisherService service) {
		this.service = service;
	}

	@Operation(summary = "Get Active Publishers Only",
			description = "This endpoint will return publishers that have ACTIVE status in DB, with paging option and search by publisher's name | (Authority) ALL.")
	@ApiResponse(responseCode = "200", description = "Found the Publishers, response with paging",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = PagingListResposneDTO.class))),
	})
	@ApiResponse(responseCode = "400", description = "Bad Request Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@ApiResponse(responseCode = "404", description = "Not Found Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@GetMapping("/store/api/v1/publishers")
	public ResponseEntity<?> getAllActivePublisher(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false, name = "query") String keyword
			) {
		
		if (keyword == null) keyword = "";
		
		return service.findAllPublisherByStatus(PublisherStatus.ACTIVE, page, size, keyword);
	}
	
	@Operation(summary = "Get Publishers",
			description = "This endpoint will return all publishers in DB, with paging option and search by publisher's name | (Authority) ADMIN, MANAGER.")
	@ApiResponse(responseCode = "200", description = "Found the Publishers, response with paging",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = PagingListResposneDTO.class))),
	})
	@ApiResponse(responseCode = "400", description = "Bad Request Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@ApiResponse(responseCode = "404", description = "Not Found Exception Response",
	useReturnTypeSchema = true,
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = MessageResponseDTO.class)),
	})
	@GetMapping("/admin/api/v1/publishers")
	public ResponseEntity<?> getAllPublisher(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = false, name = "query") String keyword
			) {
		
		if (keyword == null) keyword = "";
		
		return service.findAllPublisher(page, size, keyword);
	}
	
	@Operation(summary = "Get All Publisher's Status",
			description = "This endpoint will return all publisher's status in DB | (Authority) ADMIN, MANAGER.")
	@GetMapping("/admin/api/v1/publishers/status")
	public Set<PublisherStatus> getAllPublisherStatus() {
		return service.findAllPublisherStatus();
	}
	
	@Operation(summary = "Delete Publisher By Id",
			description = "This endpoint will soft delete publisher with the given id | (Authority) ADMIN, MANAGER")
	@DeleteMapping("/admin/api/v1/publishers/{id}")
	public ResponseEntity<?> deletePublisherById(@PathVariable("id") Integer id){
		service.deletePublisherById(id);
		
		return Utils.generateMessageResponseEntity(
				"Xóa nhà xuất bản với mã số " + id + " thành công!", 
				HttpStatus.OK);
	}
	
	@Operation(summary = "Create new Publisher",
			description = "This endpoint will create new publisher with the given information | (Authority) ADMIN, MANAGER.")
	@PostMapping("/admin/api/v1/publishers")
	public ResponseEntity<?> savePublisher(@Valid @RequestBody PublisherCreateBodyDTO publisher){
		service.savePublisher(publisher);
		
		return Utils.generateMessageResponseEntity(
				"Tạo mới nhà xuất bản thành công!", 
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Publisher data",
			description = "This endpoint will update publisher with the given information | (Authority) ADMIN, MANAGER.")
	@PutMapping("/admin/api/v1/publishers")
	public ResponseEntity<?> updatePublisher(@Valid @RequestBody PublisherUpdateBodyDTO publisher) {
		service.updatePublisher(publisher);
		
		return Utils.generateMessageResponseEntity(
				"Cập nhật nhà xuất bản thành công!", 
				HttpStatus.OK);
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
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoPublisherFoundException.class)
	public ResponseEntity<?> handleNoPublisherFoundException(
				NoPublisherFoundException ex
			){
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoPublisherExistException.class)
	public ResponseEntity<?> handleNoPublisherExistException(
				NoPublisherExistException ex
			){
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
}
