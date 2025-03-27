package com.ecommerce.product_catalog_service.service.product;

import java.util.List;

import com.ecommerce.product_catalog_service.dto.product.ProductCreateDto;
import com.ecommerce.product_catalog_service.dto.product.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductCreateDto productCreateDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductCreateDto productUpdateDto);
    void deleteProduct(Long id);
}
