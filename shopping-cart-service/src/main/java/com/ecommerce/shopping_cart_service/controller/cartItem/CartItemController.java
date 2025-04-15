package com.ecommerce.shopping_cart_service.controller.cartItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shopping_cart_service.domain.Cart;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemCreateDto;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemUpdateDto;
import com.ecommerce.shopping_cart_service.service.cart.CartService;
import com.ecommerce.shopping_cart_service.service.cartItem.CartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cartitem")
@AllArgsConstructor
@Tag(name = "Item del Carrito", description = "Operaciones relacionadas a los items de los carritos de compra.")
public class CartItemController {

    private CartItemService cartItemService;
    private CartService cartService;
    
    @Operation(summary = "Agregar un item al carrito", description = "Agregar un item al carrito, especificando el id del producto y su cantidad.")
    @ApiResponse(responseCode = "201", description = "Item del carrito creado exitosamente")
    @PostMapping()
    public ResponseEntity<?> addCartItem(@RequestBody @Valid CartItemCreateDto cartItemToAdd){
        Cart cartCreated = cartService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.addItem(cartItemToAdd, cartCreated));
    }
    
    @Operation(summary = "Actualizar un item del carrito", description = "Actualiza la cantidad de un item existente por ID")
    @ApiResponse(responseCode = "200", description = "Item actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Item no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable("id") Long idCartItem, @RequestBody @Valid CartItemUpdateDto cartItemUpdateDto){
        return ResponseEntity.ok().body(cartItemService.updateCartItem(idCartItem, cartItemUpdateDto));
    }
    
    @Operation(summary = "Eliminar un item del carrito", description = "Elimina un item del carrito por su ID.")
    @ApiResponse(responseCode = "204", description = "Item eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Item no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long idCartItem){
        cartItemService.deleteCartItem(idCartItem);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Eliminar items por ID de producto", description = "Elimina todos los items del carrito que referencian un producto específico.")
    @ApiResponse(responseCode = "204", description = "Items eliminados exitosamente")
    @DeleteMapping("/by-product/{productId}")
    public ResponseEntity<?> deleteCartItemsByProductId(@PathVariable("productId") Long productId) {
        cartItemService.deleteCartItemsByProductId(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
