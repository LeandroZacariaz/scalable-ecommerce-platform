package com.ecommerce.shopping_cart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shopping_cart_service.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    boolean existsByUserEmail(String email);
    Optional<Cart> findByUserEmail(String email);
}
