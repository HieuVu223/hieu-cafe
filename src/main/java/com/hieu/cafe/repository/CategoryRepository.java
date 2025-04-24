package com.hieu.cafe.repository;

import com.hieu.cafe.model.CategoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

//data access layer
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>
    //Inherits standard CRUD ethos from Spring Data JPA
        // Save(), findAll(), findById(), delete()
{
}
