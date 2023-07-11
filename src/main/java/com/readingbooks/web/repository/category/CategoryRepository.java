package com.readingbooks.web.repository.category;

import com.readingbooks.web.domain.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
