package com.inkmelo.user;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserMappingService {

		
	public User userCreateBodyDTOToUser(UserCreateBodyDTO userDTO) {
		return User.builder()
				.username(userDTO.username())
				.password(userDTO.password())
				.email(userDTO.email())
				.fullname(userDTO.fullname())
				.role(userDTO.role() == null ? 
						UserRole.CUSTOMER : userDTO.role())
				.createdAt(Date.valueOf(LocalDate.now()))
				.lastChangedBy(SecurityContextHolder.getContext()
						.getAuthentication().getName())
				.lastUpdatedTime(Date.valueOf(LocalDate.now()))
				.status(UserStatus.ACTIVE)
				.build();
	}

	public UserAdminResponseDTO userToUserAdminResponseDTO(User user) {
		return UserAdminResponseDTO.builder()
				.id(user.getId())
				.username(user.getUsername())
				.fullname(user.getFullname())
				.email(user.getEmail())
				.role(user.getRole())
				.createdAt(user.getCreatedAt())
				.lastChangedBy(user.getLastChangedBy())
				.lastUpdatedTime(user.getLastUpdatedTime())
				.status(user.getStatus())
				.build();
	}
}
