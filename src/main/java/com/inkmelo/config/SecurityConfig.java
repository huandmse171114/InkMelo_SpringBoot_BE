package com.inkmelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.inkmelo.auth.AuthEntryPointJwt;
import com.inkmelo.auth.AuthTokenFilter;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.user.UserRole;
import com.inkmelo.user.UserService;
import com.inkmelo.user.UserStatus;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    UserRepository repository;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//	@Bean
//	@Profile(value = "dev")
//	SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll())
////			.formLogin(withDefaults())
////		when we are not using formLogin authentication, the session id is maintain throughout the session
////		which made our server is statefull, which is wrong to RestAPI principles.
//                .sessionManagement(session -> session.sessionCreationPolicy(
//                        SessionCreationPolicy.STATELESS
//                ))
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//                .httpBasic(withDefaults());
//		return http.build();
//		
//	}  
	
	@Bean
	@Profile(value = "dev")
	SecurityFilterChain productSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
//        		.requestMatchers(
//        				"/",
//        				"/swagger-ui/**",
//        				"/api/v1/v3/api-docs/**",
//        				"/api/v1/auth/sign-in",
//        				"/api/v1/users/register").permitAll()
//        		.requestMatchers("/api/v1/admin/**").hasAuthority(UserRole.ADMIN.toString())
//        		.requestMatchers(
//        				"/api/v1/admin/books/**",
//						"/api/v1/admin/publishers/**").hasAuthority(UserRole.MANAGER.toString())
//                .requestMatchers("/api/v1/sign-in").permitAll()
                .anyRequest().permitAll());
		http.sessionManagement(
		        session ->
		                session.sessionCreationPolicy(
		                        SessionCreationPolicy.STATELESS)
		);
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
		//http.httpBasic(withDefaults());
		http.headers(headers -> headers
		        .frameOptions(frameOptions -> frameOptions
		                .sameOrigin()
		        )
		);
		http.csrf(csrf -> csrf.disable());
		http.addFilterBefore(authenticationJwtTokenFilter(),
		        UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
		
	}
	
	@Bean
	@Profile(value = "dev")
    CommandLineRunner initData(UserService userService) {
        return args -> {
            User user = User.builder()
            		.username("user1")
            		.password(passwordEncoder().encode("password1"))
            		.role(UserRole.CUSTOMER)
            		.status(UserStatus.ACTIVE)
            		.createdAt(Date.valueOf(LocalDate.now()))
            		.lastChangedBy("anonymous")
            		.lastUpdatedTime(Date.valueOf(LocalDate.now()))
            		.build();
            
            User admin = User.builder()
            		.username("admin1")
            		.password(passwordEncoder().encode("password1"))
            		.role(UserRole.ADMIN)
            		.status(UserStatus.ACTIVE)
            		.createdAt(Date.valueOf(LocalDate.now()))
            		.lastChangedBy("anonymous")
            		.lastUpdatedTime(Date.valueOf(LocalDate.now()))
            		.build();
            
            User manager = User.builder()
            		.username("manager1")
            		.password(passwordEncoder().encode("password1"))
            		.role(UserRole.MANAGER)
            		.status(UserStatus.ACTIVE)
            		.createdAt(Date.valueOf(LocalDate.now()))
            		.lastChangedBy("anonymous")
            		.lastUpdatedTime(Date.valueOf(LocalDate.now()))
            		.build();

            userService.createUser(user);
            userService.createUser(admin);
            userService.createUser(manager);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    UserDetailsService userDetailsService() {
      return username -> repository.findByUsername(username)
    		  .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
    
    @Bean
    AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
	
	
}
