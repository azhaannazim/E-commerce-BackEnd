package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category findByCategoryName(String categoryName);
}
