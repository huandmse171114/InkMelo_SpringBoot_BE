package com.inkmelo.cartdetail;

import java.util.HashMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inkmelo.exception.NoCartDetailFoundException;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoUserFoundException;
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
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Cart Detail Item", description = "Cart Detail Item Management APIs")
@RequiredArgsConstructor
public class CartDetailController {
	private final CartDetailService service;
	
	@Operation(summary = "Get Active Cart Detail Only",
			description = "This endpoint will return cart details that have ACTIVE status in DB with paging option by username | (Authority) CUSTOMER.")
	@ApiResponse(responseCode = "200", description = "Found the Cart Details, response with paging",
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
	@GetMapping("/store/api/v1/cart-details")
	public ResponseEntity<?> getAllActiveCartDetail(
				@RequestParam(required = false) Integer page,
				@RequestParam(required = false) Integer size,
				@RequestParam(required = true, name = "user") String username
			) {
		return service.findAllCartDetailByStatus(CartDetailStatus.ACTIVE, page, size, username);
	}
	
	@Operation(summary = "Modify Book Package into Customer Cart",
			description = "This endpoint will add, update book package quantity into customer cart. For deletion, please update cart item with quantity 0 | (Authority) ADMIN.")
	@PostMapping("/store/api/v1/cart-details")
	public ResponseEntity<?> modifyCartDetails(@Valid @RequestBody CartDetailCreateUpdateBodyDTO cartDetailDTO) {
		String message = service.modifyCartDetails(cartDetailDTO);
		
		return Utils.generateMessageResponseEntity(
				message, 
				HttpStatus.OK);
	}
	
	
	
	
	
//	====================================== Exception Handler ===================================
	
	@ExceptionHandler(NoUserFoundException.class)
	public ResponseEntity<?> handleNoUserFoundException(
			NoUserFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCustomerFoundException.class)
	public ResponseEntity<?> handleNoCustomerFoundException(
			NoCustomerFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoCartDetailFoundException.class)
	public ResponseEntity<?> handleNoCartDetailFoundException(
			NoCartDetailFoundException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	
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
	
}
