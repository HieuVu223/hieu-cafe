package com.hieu.cafe.serviceImplementation;

import com.hieu.cafe.model.CategoryEntity;
import com.hieu.cafe.model.ProductEntity;
import com.hieu.cafe.repository.CategoryRepository;
import com.hieu.cafe.repository.ProductRepository;
import com.hieu.cafe.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServicesImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServicesImp(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ProductEntity saveProduct(ProductEntity product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    private void validateProduct(ProductEntity product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        validatePrice(product.getPrice());
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be a positive value");
        }
    }

    @Override
    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        // Update fields with validation
        if (updatedProduct.getName() != null) {
            existing.setName(updatedProduct.getName());
        }
        if (updatedProduct.getPrice() != null) {
            validatePrice(updatedProduct.getPrice());
            existing.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getDescription() != null) {
            existing.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getCategory() != null) {
            updateCategory(existing, updatedProduct.getCategory().getId());
        }

        return productRepository.save(existing);
    }

    private void updateCategory(ProductEntity existing, Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        existing.setCategory(category);
    }


    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductEntity> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
