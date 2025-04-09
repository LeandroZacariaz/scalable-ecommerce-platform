package com.ecommerce.shopping_cart_service.service.cartItem;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.shopping_cart_service.domain.Cart;
import com.ecommerce.shopping_cart_service.domain.CartItem;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemCreateDto;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemDto;
import com.ecommerce.shopping_cart_service.dto.cartItem.CartItemUpdateDto;
import com.ecommerce.shopping_cart_service.exceptions.ResourceNotFoundException;
import com.ecommerce.shopping_cart_service.mappers.cartItem.CartItemMapper;
import com.ecommerce.shopping_cart_service.repository.CartItemRepository;
import com.ecommerce.shopping_cart_service.repository.CartRepository;
import com.ecommerce.shopping_cart_service.service.client.ProductServiceClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService{
    
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CartItemMapper cartItemMapper;
    private ProductServiceClient productServiceClient;
    
    @Override
    public CartItemDto addItem(CartItemCreateDto cartItemToAdd, Cart cartCreated) {
        CartItem cartItemCreated = cartItemMapper.cartItemCreateDtoToCartItem(cartItemToAdd);
        Long idProduct = cartItemCreated.getIdProduct();

        // Validar que el producto existe en el Product Service
        if (!productServiceClient.productExists(idProduct)) {
            throw new ResourceNotFoundException("El producto con ID " + idProduct + " no existe");
        }

        List<CartItem> items = cartCreated.getItems();
        for(CartItem item : items){
            if (item.getIdProduct().equals(cartItemCreated.getIdProduct())) {
                throw new IllegalArgumentException("El item ya existe en el carrito.");
            }
        }
        cartItemCreated.setAddedAt(LocalDateTime.now());
        cartItemCreated.setCart(cartCreated);
        items.add(cartItemCreated);
        cartCreated.setUpdateAt(LocalDateTime.now());
        cartRepository.save(cartCreated);
        return cartItemMapper.cartItemToCartItemDto(cartItemCreated);
        
    }

    @Override
    public void deleteCartItem(Long idCartItem) {
        CartItem cartItemDelete = getCartItem(idCartItem);
        String currentUserEmail= SecurityContextHolder.getContext().getAuthentication().getName();

        if (!cartItemDelete.getCart().getUserEmail().equals(currentUserEmail)) {
            throw new ResourceNotFoundException("El item del carrito con ID: " +idCartItem+" no existe." );
        }

        cartItemRepository.deleteById(idCartItem);
    }

    @Override
    public CartItemDto updateCartItem(Long idCartItem, CartItemUpdateDto cartItemUpdateDto) {
        CartItem cartItemUpdate=getCartItem(idCartItem);
        String currentUserEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        if (!cartItemUpdate.getCart().getUserEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("No tienes permiso para modificar este item");
        }
        cartItemUpdate.setQuantity(cartItemUpdateDto.quantity());
        return cartItemMapper.cartItemToCartItemDto(cartItemRepository.save(cartItemUpdate));
    }


    @Override
    public CartItem getCartItem(Long idCartItem){
        return cartItemRepository.findById(idCartItem).orElseThrow(() -> 
                new ResourceNotFoundException("CartItem con ID: " + idCartItem + " no encontrado."));
    }
}
