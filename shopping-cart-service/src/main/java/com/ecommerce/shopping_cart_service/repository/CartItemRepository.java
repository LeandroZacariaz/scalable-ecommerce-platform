package com.ecommerce.shopping_cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shopping_cart_service.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
