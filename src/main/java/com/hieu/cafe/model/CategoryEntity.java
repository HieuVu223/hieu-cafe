package com.hieu.cafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    private String name;
    private String description;
    //No need for id/createdAt - inherited!
}
