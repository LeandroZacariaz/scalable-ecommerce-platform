package com.ecommerce.shopping_cart_service.dto.cart;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar una carrito sin usar la entidad directamente")
public record CartDto(
    @Schema(description = "Identificador único del carrito", example = "1")
    Long idCart,
    @Schema(description = "Fecha y hora de creación del carrito", example = "2025-04-08T10:30:00")
    LocalDateTime createAt,
    @Schema(description = "Fecha y hora de la última actualización del carrito", example = "2025-04-08T12:45:00")
    LocalDateTime updateAt,
    @Schema(description = "Lista de ítems contenidos en el carrito")
    List<CartItemDto> items
) {

}
