package com.studentmanagementapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/auth/**").permitAll()
            	    
            	    
            	    // Swagger access
            	    .requestMatchers(
            	    	    "/swagger-ui/**",
            	    	    "/v3/api-docs/**",
            	    	    "/swagger-ui.html",
            	    	    "/swagger-resources/**",
            	            "/webjars/**"
            	    	).permitAll()
            	    
            	       
            	    // 👇 allow both roles to VIEW
            	    .requestMatchers(org.springframework.http.HttpMethod.GET, "/students/**")
            	    .hasAnyRole("USER", "ADMIN")

            	    // 👇 only ADMIN can modify
            	    .requestMatchers(org.springframework.http.HttpMethod.POST, "/students/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers(org.springframework.http.HttpMethod.PUT, "/students/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/students/**")
            	    .hasRole("ADMIN")

            	    .anyRequest().authenticated()
            	)
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}