package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    String updateCategory(Category category, Long categoryId);
}
