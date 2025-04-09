package com.ecommerce.shopping_cart_service.mappers.cartItem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.shopping_cart_service.domain.CartItem;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemCreateDto;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemDto;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "idCartItem", ignore = true)
    @Mapping(target = "addedAt", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem cartItemCreateDtoToCartItem(CartItemCreateDto cartItemCreateDto);

    CartItemDto cartItemToCartItemDto(CartItem cartItem);
}
