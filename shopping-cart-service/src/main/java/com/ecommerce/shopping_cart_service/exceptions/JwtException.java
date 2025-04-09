package com.ecommerce.shopping_cart_service.exceptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
       super(message);
    }
 }
 