package com.hieu.cafe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@MappedSuperclass
@Data


public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Replace failed automatic getter from lombok
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @CreationTimestamp
    private LocalDateTime createdAt;
}
