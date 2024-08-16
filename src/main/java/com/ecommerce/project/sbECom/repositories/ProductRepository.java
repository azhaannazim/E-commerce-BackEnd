package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
