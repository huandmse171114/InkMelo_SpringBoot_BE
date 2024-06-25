package com.inkmelo.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.inkmelo.utils.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Authentication Management APIs")
@RestController
public class AuthController {
	
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;

	public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
		super();
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
	}

	@Operation(summary = "Authentication User", description = "This endpoint is used for user to sign in. Return JWT Token in response")
	@PostMapping("/store/api/v1/auth/sign-in")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginBodyDTO loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponseDTO response = LoginResponseDTO.builder()
        		.username(userDetails.getUsername())
        		.jwtToken(jwtToken)
        		.roles(roles)
        		.build();
        
        return ResponseEntity.ok(response);
    }
}
