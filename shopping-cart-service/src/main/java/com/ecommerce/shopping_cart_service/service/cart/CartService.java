package com.ecommerce.shopping_cart_service.service.cart;

import com.ecommerce.shopping_cart_service.domain.Cart;
import com.ecommerce.shopping_cart_service.dto.cart.CartDto;

public interface CartService {
    Cart createCart();

    void deleteCart();

    CartDto getCart();
}
