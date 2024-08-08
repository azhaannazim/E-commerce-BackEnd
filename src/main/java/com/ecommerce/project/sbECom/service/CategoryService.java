package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.payload.CategoryDTO;
import com.ecommerce.project.sbECom.payload.CategoryResponse;
import org.hibernate.id.IntegralDataTypeHolder;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
