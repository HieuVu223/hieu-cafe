package com.hieu.cafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor

public class CategoryEntity extends BaseEntity {
    private String name;
    private String description;
    //No need for id/createdAt - inherited!

    //replace failed automatic getter setter from lombok
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return super.getId();  // Calls parent's getter
    }


}

