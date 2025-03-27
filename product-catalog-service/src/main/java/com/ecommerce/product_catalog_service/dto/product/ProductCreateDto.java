package com.ecommerce.product_catalog_service.dto.product;

public record ProductCreateDto(
    String name,
    String description,
    Double price,
    Integer stock,
    String name_category
) {

}
