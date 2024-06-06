package com.inkmelo.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.inkmelo.exception.NoUserExistException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.exception.PasswordConfirmIsDifferentException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users", description = "Users Management APIs")
@RestController
public class UserController {
	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}
	
	@Operation(summary = "Get All Users",
			description = "This endpoint will return all users in DB | (Authority) ADMIN.")
	
	@GetMapping("/admin/users")
	public List<UserAdminResponseDTO> getAllUsers() {
		return service.findAllUsers();
	}
	
	@Operation(summary = "Register new User",
			description = "This endpoint will create new user | (Authority) ALL.")
	@PostMapping("/users/register")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserCreateBodyDTO userDTO) {
		var response = new HashMap<String, Object>();
		service.saveUser(userDTO);
		response.put("message", "Register new account successfully!");
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.CREATED.value());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
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
	
	@ExceptionHandler(NoUserFoundException.class)
	public ResponseEntity<?> handleNoUserFoundException(
				NoUserFoundException ex
			){
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoUserExistException.class)
	public ResponseEntity<?> handleNoUserExistException(
				NoUserExistException ex
			){
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NO_CONTENT.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(PasswordConfirmIsDifferentException.class)
	public ResponseEntity<?> handlePasswordConfirmIsDifferentException(
				PasswordConfirmIsDifferentException ex
			) {
		var response = new HashMap<String, Object>();
		
		response.put("timestamp", Utils.getCurrentTimestamp());
		response.put("status", HttpStatus.NO_CONTENT.value());
		response.put("message", ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}
}
