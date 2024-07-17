package com.inkmelo.user;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMappingService {
	private final PasswordEncoder passwordEncoder;

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