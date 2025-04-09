package com.ecommerce.product_catalog_service.service.product;

import java.util.List;
import org.springframework.stereotype.Service;

import com.ecommerce.product_catalog_service.domain.Product;
import com.ecommerce.product_catalog_service.dto.product.ProductCreateDto;
import com.ecommerce.product_catalog_service.dto.product.ProductDto;
import com.ecommerce.product_catalog_service.exceptions.ResourceNotFoundException;
import com.ecommerce.product_catalog_service.mappers.product.ProductMapper;
import com.ecommerce.product_catalog_service.repository.CategoryRepository;
import com.ecommerce.product_catalog_service.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        Product productCreated=productMapper.productCreateDtoToProduct(productCreateDto);
        productCreated.setCategory(categoryRepository.findByName(productCreateDto.name_category()).orElseThrow(() 
                -> new ResourceNotFoundException("La categoría con nombre: " + productCreateDto.name_category() + " no existe.")));
        return productMapper.productToProductDto(productRepository.save(productCreated));
    }
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::productToProductDto)
                                .orElseThrow(() -> 
                                new ResourceNotFoundException("El producto con ID: " + id + " no existe."));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductCreateDto productUpdateDto) {
        Product productUpdate=productRepository.findById(id).orElseThrow();
        productUpdate.setName(productUpdateDto.name());
        productUpdate.setDescription(productUpdateDto.description());
        productUpdate.setPrice(productUpdateDto.price());
        productUpdate.setStock(productUpdateDto.stock());
        productUpdate.setCategory(categoryRepository.findByName(productUpdateDto.name_category()).orElseThrow(() 
                    -> new ResourceNotFoundException("La categoría con nombre: " + productUpdateDto.name_category() + " no existe.")));
        return productMapper.productToProductDto(productRepository.save(productUpdate));
    }

    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("El producto con ID: " +id+" no existe." );
        }
    }

    

}
