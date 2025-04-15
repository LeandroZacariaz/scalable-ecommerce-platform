package com.ecommerce.product_catalog_service.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CartItemServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(CartItemServiceClient.class);
    private final RestTemplate restTemplate;

    @Value("${cart.service.url:http://shopping-cart-service:8083}")
    private String cartServiceUrl;

    public CartItemServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        logger.info("URL del Cart Service configurada como: {}", cartServiceUrl);
    }

    public void deleteCartItemsByProductId(Long productId) {
        String url = cartServiceUrl + "/cartitem/by-product/" + productId;
        logger.info("Eliminando ítems del carrito para productId: {}", productId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication != null && authentication.getCredentials() != null
            ? authentication.getCredentials().toString()
            : null;

        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        } else {
            logger.warn("No se encontró token JWT en el contexto de seguridad");
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
            logger.info("Ítems del carrito eliminados exitosamente para productId: {}", productId);
        } catch (Exception e) {
            logger.error("Error al eliminar ítems del carrito: {}", e.getMessage());
            throw new RuntimeException("No se pudieron eliminar los ítems del carrito para el producto con ID " + productId);
        }
    }
}