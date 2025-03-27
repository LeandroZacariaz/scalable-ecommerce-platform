package com.ecommerce.product_catalog_service.mappers.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.product_catalog_service.domain.Product;
import com.ecommerce.product_catalog_service.dto.product.ProductCreateDto;
import com.ecommerce.product_catalog_service.dto.product.ProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    @Mapping(target = "id_product", ignore = true)
    @Mapping(source = "name_category", target = "category.name")
    Product productCreateDtoToProduct(ProductCreateDto productDto);
    
}
