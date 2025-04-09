package com.ecommerce.shopping_cart_service.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartItem;

    @Column(nullable = false, unique = false)
    private Long idProduct;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Cart cart;

    @Column(nullable = false)
    private LocalDateTime addedAt;
}
