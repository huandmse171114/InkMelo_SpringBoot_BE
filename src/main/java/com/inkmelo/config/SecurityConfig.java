package com.inkmelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	@Profile(value = "dev")
	SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//			.formLogin(withDefaults())
//		when we are not using formLogin authentication, the session id is maintain throughout the session
//		which made our server is statefull, which is wrong to RestAPI principles.
			.sessionManagement(session -> session.sessionCreationPolicy(
						SessionCreationPolicy.STATELESS
					))
			.httpBasic(withDefaults());
		return http.build();
		
	}
	
	@Bean
	@Profile(value = "prod")
	SecurityFilterChain productSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
			.formLogin(withDefaults())
			.httpBasic(withDefaults());
		return http.build();
		
	}
}
