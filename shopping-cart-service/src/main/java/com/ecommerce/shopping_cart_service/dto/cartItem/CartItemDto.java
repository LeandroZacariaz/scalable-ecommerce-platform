package com.ecommerce.shopping_cart_service.dto.cartItem;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de un item del carrito")
public record CartItemDto(
    @Schema(description = "Identificador único del producto", example = "1")
    Long idProduct,
    @Schema(description = "Cantidad de productos", example = "3")
    Integer quantity,
    @Schema(description = "Fecha y hora en la que se agrego el item al carrito", example = "2025-04-08T10:30:00")
    LocalDateTime addedAt

) {

}
