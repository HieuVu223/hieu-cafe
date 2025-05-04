package com.hieu.cafe.model;

import jakarta.persistence.*;


import java.math.BigDecimal;

@Entity
@Table(name = "products")

public class ProductEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean status = true;

    // Add manual getters/setters for all fields
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public CategoryEntity getCategory() { return category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(CategoryEntity category) { this.category = category; }
}
