package com.hieu.cafe.controller;

import com.hieu.cafe.model.CategoryEntity;
import com.hieu.cafe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Create
    @PostMapping
    public ResponseEntity<CategoryEntity>
    createCategory(@RequestBody CategoryEntity category){
        CategoryEntity savedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //Read (All)
    @GetMapping
    public ResponseEntity<List<CategoryEntity>>
    getAllCategories() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //Read (Single)
    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id).map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity <CategoryEntity>
    updateCategory(@PathVariable Long id, @RequestBody CategoryEntity updatedCategory){
        try {
            CategoryEntity category = categoryService.updateCategory(id, updatedCategory);
            return ResponseEntity.ok(category);
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/test")
    public ResponseEntity<?> testEndpoint(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(request); // Echo back the input
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();

    }
}
