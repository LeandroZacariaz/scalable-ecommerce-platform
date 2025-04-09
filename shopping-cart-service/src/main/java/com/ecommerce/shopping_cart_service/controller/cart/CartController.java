package com.ecommerce.shopping_cart_service.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shopping_cart_service.dto.cart.CartDto;
import com.ecommerce.shopping_cart_service.service.cart.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Tag(name = "Carrito", description = "Operaciones relacionadas a los carritos de compra.")
public class CartController {

    private CartService cartService;

    @Operation(summary = "Eliminar el carrito", description = "Elimina el carrito del usuario logueado.")
    @ApiResponse(responseCode = "204", description = "Carrito eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteCart(){
        cartService.deleteCart();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Obtener carrito", description = "Devuelve el carrito del usuario logueado.")
    @ApiResponse(responseCode = "200", description = "Carrito obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    @GetMapping("/me")
    public CartDto getCart(){
        return cartService.getCart();
    }

}
