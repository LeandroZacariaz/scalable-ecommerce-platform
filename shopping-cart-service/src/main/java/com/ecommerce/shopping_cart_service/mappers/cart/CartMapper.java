package com.ecommerce.shopping_cart_service.mappers.cart;

import org.mapstruct.Mapper;

import com.ecommerce.shopping_cart_service.domain.Cart;
import com.ecommerce.shopping_cart_service.dto.cart.CartDto;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto cartToCartDto(Cart cart);
}
