package com.hieu.cafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")

public class CategoryEntity extends BaseEntity {
    private String name;
    @Column(name = "description")
    @JsonProperty("description")
    private String description;
    //No need for id/createdAt - inherited!

    //replace failed automatic getter setter from lombok
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Add explicit description getter/setter
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return super.getId();  // Calls parent's getter
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Prevents infinite recursion
    private List<ProductEntity> products = new ArrayList<>();

    // Helper method to sync both sides of the relationship
    public void addProduct(ProductEntity product) {
        products.add(product);
        product.setCategory(this);}

    // Getters/setters
    public List<ProductEntity> getProducts() { return products; }
    public void setProducts(List<ProductEntity> products) { this.products = products; }
}

