package com.hieu.cafe.service;

import com.hieu.cafe.model.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryEntity saveCategory(CategoryEntity category);
    List<CategoryEntity> getAllCategories();

    //New methods for CRUD
    Optional<CategoryEntity> getCategoryById(Long id);
    CategoryEntity updateCategory(Long id, CategoryEntity updatedCategory);
    void deleteCategory(Long id);
}
