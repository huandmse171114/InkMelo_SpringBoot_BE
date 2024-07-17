package com.inkmelo.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.inkmelo.user.ProviderType;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.user.UserRole;
import com.inkmelo.user.UserService;
import com.inkmelo.user.UserStatus;
import com.inkmelo.utils.JwtUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final UserService userService;
	private final JwtUtils jwtUtils;
	private final GoogleIdTokenVerifier verifier;
	
	
	
	public AuthService(@Value("${app.googleClientId}") String clientId, UserRepository userRepository, 
			UserService userService, JwtUtils jwtUtils) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
		this.jwtUtils = jwtUtils;
		NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
		this.verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
	}

	public ResponseEntity<?> authenticateGoogleUser(GoogleTokenBodyDTO tokenDTO) {
		User user = verifyIDToken(tokenDTO.credential());
		if (user == null) {
			throw new IllegalArgumentException("Credential không hợp lệ.");
		}
		
		user = createOrUpdateUser(user);
		
		List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
		
		LoginResponseDTO response = LoginResponseDTO.builder()
        		.username(user.getUsername())
        		.jwtToken(jwtUtils.generateTokenFromUsername(user.getUsername()))
        		.roles(roles)
        		.build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private User verifyIDToken(String idToken) {
		try {
			GoogleIdToken idTokenObj = verifier.verify(idToken);
			if (idTokenObj == null) {
				return null;
			}
			
			GoogleIdToken.Payload payload = idTokenObj.getPayload();
			String firstName = (String) payload.get("given_name");
			String lastName = (String) payload.get("family_name");
			String email = payload.getEmail();
			String pictureUrl = (String) payload.get("picture");
			
			return User.builder()
					.email(email)
					.fullname(firstName + " " + lastName)
					.profileImg(pictureUrl)
					.provider(ProviderType.GOOGLE)
					.build();
			
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	private User createOrUpdateUser(User user) {
		Optional<User> existingUserOptional = userRepository.findByEmail(user.getEmail());
		if (existingUserOptional.isEmpty()) {
			user.setUsername(user.getEmail());
			user.setRole(UserRole.CUSTOMER);
			user.setStatus(UserStatus.ACTIVE);
			user.setCreatedAt(Date.valueOf(LocalDate.now()));
			user.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
			user.setLastChangedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			return userService.createUser(user);
		}
		
		User existingUser = existingUserOptional.get();
		existingUser.setFullname(user.getFullname());
		existingUser.setProfileImg(user.getProfileImg());
		return userRepository.save(existingUser);
	}
	
}
