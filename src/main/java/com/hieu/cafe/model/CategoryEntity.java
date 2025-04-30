package com.hieu.cafe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


}

