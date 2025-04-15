package com.ecommerce.product_catalog_service.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.product_catalog_service.service.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        logger.info("Encabezado Authorization recibido: {}", request.getHeader("Authorization"));

        if (token != null) {
            try {
                String email = jwtService.getEmailFromToken(token);
                logger.info("Token JWT extraído, email: {}", email);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String role = jwtService.getRoleFromToken(token);
                    List<SimpleGrantedAuthority> authorities = role != null
                            ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                            : Collections.emptyList();

                    if (jwtService.isTokenValid(token, email)) {
                        // Almacenar el token como credencial
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                email, token, authorities);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
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

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
    }
}