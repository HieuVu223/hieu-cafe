package com.hieu.cafe.service;

import com.hieu.cafe.model.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductEntity saveProduct(ProductEntity product); // With validations
    List<ProductEntity> getAllProducts();
    Optional<ProductEntity> getProductById(Long id);
    ProductEntity updateProduct(Long id, ProductEntity updatedProduct); // With validations
    void deleteProduct(Long id);
    // Extra product-specific method
    List<ProductEntity> getProductsByCategory(Long categoryId);
}
