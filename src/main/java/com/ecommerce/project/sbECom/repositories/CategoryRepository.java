package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
}
