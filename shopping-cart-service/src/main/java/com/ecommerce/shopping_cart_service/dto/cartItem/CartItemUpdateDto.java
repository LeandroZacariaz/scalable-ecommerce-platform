package com.ecommerce.shopping_cart_service.dto.cartItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "DTO para la actualización de un item del carrito")
public record CartItemUpdateDto(
    @NotNull(message = "The product quantity cannot be null.")
    @Positive(message = "The product quantity must be greater than zero.")
    @Schema(description = "Cantidad de productos", example = "3")
    Integer quantity
) {

}
