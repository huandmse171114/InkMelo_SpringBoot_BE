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
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.inkmelo.auth.AuthEntryPointJwt;
import com.inkmelo.auth.AuthTokenFilter;
import com.inkmelo.cartdetail.CartDetail;
import com.inkmelo.cartdetail.CartDetailCreateUpdateBodyDTO;
import com.inkmelo.cartdetail.CartDetailRepository;
import com.inkmelo.cartdetail.CartDetailService;
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
	private CartDetailService cartDetailService;

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
        		.requestMatchers(
        				"/",
        				"/swagger-ui/**",
        				"/v3/api-docs/**",
        				"/store/api/v1/auth/sign-in",
        				"/store/api/v1/users/register",
        				"/store/api/v1/**").permitAll()
//        		.requestMatchers("/admin/api/v1/**").hasAuthority(UserRole.ADMIN.toString())
//        		.requestMatchers(
//        				"/admin/api/v1/books/**",
//        				"/admin/api/v1/book-items/**",
//        				"/admin/api/v1/book-packages/**",
//        				"/admin/api/v1/orders/**",
//						"/admin/api/v1/publishers/**").hasAuthority(UserRole.MANAGER.toString())
                .anyRequest().authenticated());
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
	
    
	
//    @Bean
//    @Profile(value = "dev")
//    SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
//    	http.csrf(csrf -> csrf.disable())
//            .authorizeRequests()
//                .requestMatchers("/store/api/v1/ratings/**").permitAll()
//                .anyRequest().authenticated()
//            .and()
//            .sessionManagement(
//    		        session ->
//	                session.sessionCreationPolicy(
//	                        SessionCreationPolicy.STATELESS)
//	);
//
//        return http.build();
//    }
    
}
