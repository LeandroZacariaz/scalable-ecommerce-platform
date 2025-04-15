package com.ecommerce.shopping_cart_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.shopping_cart_service.jwt.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
         .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(authRequest -> authRequest
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/cartitem/by-product/**").permitAll()
            .anyRequest().authenticated()
         )
         .sessionManagement(sessionManager -> sessionManager
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         )
         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
         .build();
    }
}
