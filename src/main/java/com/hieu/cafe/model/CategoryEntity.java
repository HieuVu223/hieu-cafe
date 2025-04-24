package com.hieu.cafe.model;

import jakarta.persistence.Entity;

@Entity
public class CategoryEntity extends BaseEntity {
    private String name;
    private String description;
    //No need for id/createdAt - inherited!
}
