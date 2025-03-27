package com.ecommerce.product_catalog_service.dto.product;

public record ProductDto(
    Long id_product,
    String name,
    String description,
    Double price,
    Integer stock
) {

}
