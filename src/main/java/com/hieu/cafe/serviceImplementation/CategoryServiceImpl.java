package com.hieu.cafe.serviceImplementation;

import com.hieu.cafe.model.CategoryEntity; //Entity Class
import com.hieu.cafe.repository.CategoryRepository; //Database operations
import com.hieu.cafe.service.CategoryService; //Service interface
import org.springframework.stereotype.Service; //Marks this as a service layer
import org.springframework.transaction.annotation.Transactional;

import java.util.List; // For list return type
import java.util.Optional;

//Business layer


@Service
public class CategoryServiceImpl implements CategoryService {

    //replace @Slf4j lombok (failed to handle the logger) with Manual Logger + Constructor Injection
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    // Add this constructor
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional  // Add this for atomic operation
    public CategoryEntity saveCategory(CategoryEntity category) {
        // 1. Input validation
        log.info("Attempting to save category: {}", category.getName());
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name required!");
        }

        // 2. Single save operation
        CategoryEntity savedCategory = categoryRepository.save(category);

        // 3. Log and return
        log.info("Successfully saved category with ID: {}", savedCategory.getId());
        return savedCategory;  // Return the already-saved entity
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        log.debug("Fetching all categories"); //Debug-level log
        return categoryRepository.findAll();//Fetches all
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public CategoryEntity updateCategory(Long id, CategoryEntity updatedCategory) {
        return categoryRepository.findById(id)//find the category
                .map(category -> { // if found, update it
                    //Only update non-null field from the request
                    CategoryEntity existing = categoryRepository.findById(id) //find the category
                            .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
                    //Copy ALL fields including nulls
                    existing.setName(updatedCategory.getName());//overwrite fields
                    existing.setDescription(updatedCategory.getDescription());

                    // Add other fields to update as needed
                    return categoryRepository.save(category);//save changes
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id)); // Throw errors if not found
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}
