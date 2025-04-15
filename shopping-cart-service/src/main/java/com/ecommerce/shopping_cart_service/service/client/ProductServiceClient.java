package com.ecommerce.shopping_cart_service.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Component
public class ProductServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceClient.class);
    private final RestTemplate restTemplate;

    @Value("${product.service.url:http://product-catalog-service:8082}")
    private String productServiceUrl;

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        logger.info("URL del Product Service configurada como: {}", productServiceUrl);
    }

    public boolean productExists(Long idProduct) {
        String url = productServiceUrl + "/product/" + idProduct;
        logger.info("Intentando validar producto en: {}", url);

        // Obtener el token JWT del contexto de seguridad
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
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            logger.info("Respuesta del Product Service: Status {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            logger.error("Error al validar producto: {}", e.getMessage());
            return false;
        }
    }
}