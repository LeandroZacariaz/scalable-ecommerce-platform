package com.ecommerce.product_catalog_service.controller.category;

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

import com.ecommerce.product_catalog_service.dto.category.CategoryCreateDto;
import com.ecommerce.product_catalog_service.service.category.CategoryService;

import lombok.AllArgsConstructor;

@RequestMapping("/category")
@RestController
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody CategoryCreateDto categoryCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryCreateDto));
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id_category){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id_category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id_category){
        categoryService.deleteCategory(id_category);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id_category, @RequestBody CategoryCreateDto categoryCreateDto){
        return ResponseEntity.ok().body(categoryService.updateCategory(id_category, categoryCreateDto));
    }
}
