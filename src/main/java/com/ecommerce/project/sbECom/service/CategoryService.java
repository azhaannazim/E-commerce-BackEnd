package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.payload.CategoryDTO;
import com.ecommerce.project.sbECom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    String deleteCategory(Long categoryId);
    String updateCategory(Category category, Long categoryId);
}
