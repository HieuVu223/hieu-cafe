package com.hieu.cafe.repository;

import com.hieu.cafe.model.CategoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
