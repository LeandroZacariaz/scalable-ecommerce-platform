package com.ecommerce.shopping_cart_service.dto.cartItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "DTO para la creación de un item del carrito")
public record CartItemCreateDto(
    @NotNull(message = "The product ID cannot be null.")
    @NotBlank(message = "The product ID cannot be empty.")
    @Positive(message = "The product ID must be greater than zero.")
    @Schema(description = "Identificador único del producto", example = "1")
    Long idProduct,
    @NotNull(message = "The product quantity cannot be null.")
    @Positive(message = "The product quantity must be greater than zero.")
    @Schema(description = "Cantidad de productos", example = "3")
    Integer quantity
) {

}
