package com.ecommerce.shopping_cart_service.service.cart;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.shopping_cart_service.domain.Cart;
import com.ecommerce.shopping_cart_service.dto.cart.CartDto;
import com.ecommerce.shopping_cart_service.exceptions.ResourceNotFoundException;
import com.ecommerce.shopping_cart_service.mappers.cart.CartMapper;
import com.ecommerce.shopping_cart_service.repository.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{
    
    private CartRepository cartRepository;
    private CartMapper cartMapper;

    @Override
    public Cart createCart() {
        String currentUserEmail= SecurityContextHolder.getContext().getAuthentication().getName();

        if (!cartRepository.existsByUserEmail(currentUserEmail)) {
            Cart cartCreate = new Cart();
            cartCreate.setUserEmail(currentUserEmail);
            cartCreate.setCreateAt(LocalDateTime.now());
            cartCreate.setUpdateAt(LocalDateTime.now());

            return cartRepository.save(cartCreate);
        }else{
            return cartRepository.findByUserEmail(currentUserEmail).orElseThrow(() 
                        -> new ResourceNotFoundException("Carrito no encontrado para el usuario: " + currentUserEmail));
        }
    }

    @Override
    public void deleteCart() {
        String currentUserEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cartDelete=cartRepository.findByUserEmail(currentUserEmail).orElseThrow(() 
                        -> new ResourceNotFoundException("Carrito no encontrado para el usuario: " + currentUserEmail));
        cartRepository.delete(cartDelete);
    }

    @Override
    public CartDto getCart() {
        String currentUserEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        CartDto cartDetail=cartMapper.cartToCartDto(cartRepository.findByUserEmail(currentUserEmail).orElseThrow(() 
                        -> new ResourceNotFoundException("Carrito no encontrado para el usuario: " + currentUserEmail)));
        return cartDetail;
    }
    
}
