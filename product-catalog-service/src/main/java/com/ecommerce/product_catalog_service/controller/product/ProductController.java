package com.ecommerce.product_catalog_service.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product_catalog_service.dto.product.ProductCreateDto;
import com.ecommerce.product_catalog_service.dto.product.ProductDto;
import com.ecommerce.product_catalog_service.service.product.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateDto productCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productCreateDto));
    }

    @GetMapping()
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id_product){
        return ResponseEntity.ok().body(productService.getProductById(id_product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id_product, @RequestBody ProductCreateDto productCreateDto){
        return ResponseEntity.ok().body(productService.updateProduct(id_product, productCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id_product){
        productService.deleteProduct(id_product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
