package com.hieu.cafe.service;

import com.hieu.cafe.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    CategoryEntity saveCategory(CategoryEntity category);
    List<CategoryEntity> getAllCategories();
}
