package com.ecommerce.shopping_cart_service.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.shopping_cart_service.service.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        logger.info("Encabezado Authorization recibido: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            logger.info("Token JWT extraído: {}", token);

            try {
                String email = jwtService.getEmailFromToken(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtService.isTokenValid(token, email)) {
                        String role = jwtService.getRoleFromToken(token);
                        List<SimpleGrantedAuthority> authorities = role != null
                                ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                                : Collections.emptyList();

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                email, token, authorities);
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        logger.info("Contexto de seguridad actualizado con token válido para email: {}", email);
                    } else {
                        logger.warn("Token JWT inválido o expirado");
                    }
                } else {
                    logger.warn("No se pudo extraer el email del token o ya hay una autenticación activa");
                }
            } catch (Exception e) {
                logger.error("Error al procesar el token JWT: {}", e.getMessage());
            }
        } else {
            logger.warn("No se encontró encabezado Authorization válido en la solicitud");
        }

        chain.doFilter(request, response);
    }
}