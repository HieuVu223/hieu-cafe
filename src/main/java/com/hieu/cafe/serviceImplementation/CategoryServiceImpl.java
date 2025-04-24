package com.hieu.cafe.serviceImplementation;

import com.hieu.cafe.model.CategoryEntity; //Entity Class
import com.hieu.cafe.repository.CategoryRepository; //Database operations
import com.hieu.cafe.service.CategoryService; //Service interface
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired; //Dependency injection
import org.springframework.stereotype.Service; //Marks this as a service layer

import java.util.List; // For list return type

//Business layer

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {
        log.info("Attempting to save category: {}", category.getName()); //Log Input
        if (category.getName() == null || category.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Category name required!");
        }
        CategoryEntity saveCategory = categoryRepository.save(category);
        log.info("Successful save category with ID: {}", saveCategory.getId()); //Log success
        return categoryRepository.save(category); // save to Db
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        log.debug("Fetching all categories"); //Debug-level log
        return categoryRepository.findAll();//Fetches all
    }
}
