package com.inkmelo.user;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inkmelo.exception.NoPublisherExistException;
import com.inkmelo.exception.NoUserExistException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.exception.PasswordConfirmIsDifferentException;
import com.inkmelo.publisher.PublisherAdminResponseDTO;

import jakarta.validation.Valid;


@Service
public class UserService {
	private final UserRepository repository;
	private final UserMappingService mappingService;
	
	public UserService(UserRepository repository, UserMappingService mappingService) {
		super();
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public User loadUserByUsername(String username) {
		Optional<User> userOptional = repository.findByUsername(username);
		
		if (userOptional.isEmpty()) {
			return null;
		}
		
		return userOptional.get();
	}

	public void createUser(User user) {
		repository.save(user);
	}

	public void saveUser(UserCreateBodyDTO userDTO) throws DataIntegrityViolationException,
		PasswordConfirmIsDifferentException {
		if (!userDTO.confirmPassword().equals(userDTO.password())) {
			throw new PasswordConfirmIsDifferentException("Password Confirm is different.");
		}
		User user = mappingService.userCreateBodyDTOToUser(userDTO);
		
		repository.save(user);
		
	}

	public List<UserAdminResponseDTO> findAllUsers() {
		var users = repository.findAll();
		
		if (users.isEmpty()) {
			throw new NoUserExistException("User data is empty.");
		}
		
		return users.stream()
				.map(user -> 
					mappingService
						.userToUserAdminResponseDTO(user))
				.sorted(new Comparator<UserAdminResponseDTO>() {
					@Override
					public int compare(UserAdminResponseDTO o1, UserAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}
	
	public User getByResetPasswordToken(String token) throws NoUserFoundException {
		Optional<User> userOption = repository.findByResetPasswordToken(token);
		
		if (userOption.isEmpty()) {
			throw new NoUserFoundException("User is not found.");
		}

		return userOption.get();
	}
	
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		String encodedPassword = pwdEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		
		user.setResetPasswordToken(null);
		repository.save(user);
	}
	
	public void updateResetPasswordToken(String token, String username) throws NoUserFoundException {
		Optional<User> userOption = repository.findByUsername(username);
		
		if (userOption.isEmpty()) {
			throw new NoUserFoundException("User is not found.");
		}
		
		User user = userOption.get();
		user.setResetPasswordToken(token);
		repository.save(user);
	}
	
}
