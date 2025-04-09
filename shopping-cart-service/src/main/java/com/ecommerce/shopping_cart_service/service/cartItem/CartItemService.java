package com.ecommerce.shopping_cart_service.service.cartItem;

import com.ecommerce.shopping_cart_service.domain.Cart;
import com.ecommerce.shopping_cart_service.domain.CartItem;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemCreateDto;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemDto;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemUpdateDto;

public interface CartItemService {

    CartItemDto addItem(CartItemCreateDto cartItemToAdd, Cart cartCreated);

    void deleteCartItem(Long idCartItem);

    CartItemDto updateCartItem(Long idCartItem, CartItemUpdateDto cartItemUpdateDto);

    CartItem getCartItem(Long idCartItem);
}
