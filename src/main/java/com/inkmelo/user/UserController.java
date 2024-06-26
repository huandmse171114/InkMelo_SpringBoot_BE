package com.inkmelo.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.inkmelo.exception.NoUserExistException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.exception.PasswordConfirmIsDifferentException;
import com.inkmelo.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
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
	
	@GetMapping("/admin/api/v1/users")
	public List<UserAdminResponseDTO> getAllUsers() {
		return service.findAllUsers();
	}
	
	@Operation(summary = "Register new User",
			description = "This endpoint will create new user | (Authority) ALL.")
	@PostMapping("store/api/v1/users/register")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserCreateBodyDTO userDTO) {
		service.saveUser(userDTO);
		
		return Utils.generateMessageResponseEntity(
				"Register new account successfully!", 
				HttpStatus.CREATED);
	}
	
//	@PostMapping("/users/password")
//	public ResponseEntity<?> updatePassword(@RequestBody String password) {
//		
//	}
	
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
	
	@ExceptionHandler(NoUserFoundException.class)
	public ResponseEntity<?> handleNoUserFoundException(
				NoUserFoundException ex
			){
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoUserExistException.class)
	public ResponseEntity<?> handleNoUserExistException(
				NoUserExistException ex
			){
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(PasswordConfirmIsDifferentException.class)
	public ResponseEntity<?> handlePasswordConfirmIsDifferentException(
				PasswordConfirmIsDifferentException ex
			) {
		
		return Utils.generateMessageResponseEntity(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST);
	}
	@Operation(summary = "Request password reset", description = "This endpoint sends a password reset token to the user's email.")
	@PostMapping("/store/api/v1/users/reset-password/{email}")
	public ResponseEntity<?> requestPasswordReset(@PathVariable String email) throws MessagingException {
	    service.updateResetPasswordToken(email);
	    return Utils.generateMessageResponseEntity("Password reset email sent.", HttpStatus.OK);
	}

	@Operation(summary = "Reset password", description = "This endpoint resets the user's password using the provided token.")
	@PostMapping("/store/api/v1/users/reset-password-confirm/{token}/{newPassword}")
	public ResponseEntity<?> resetPassword(@PathVariable String token, @PathVariable String newPassword) {
	    User user = service.getByResetPasswordToken(token);
	    service.updatePassword(user, newPassword);
	    return Utils.generateMessageResponseEntity("Password has been reset successfully.", HttpStatus.OK);
	}
}
