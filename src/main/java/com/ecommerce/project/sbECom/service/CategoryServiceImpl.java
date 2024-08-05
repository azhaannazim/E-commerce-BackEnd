package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> list = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return list;
    }
    @Override
    public void createCategory(Category category) {
        list.add(category);
        category.setCategoryId((long) list.size());
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = list.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource not found"));

        list.remove(category);
        return "successfully delete category : " + category.getCategoryName();
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Category prevCategory = list.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource not found"));

        prevCategory.setCategoryName(category.getCategoryName());
        return "successfully updated category with id : " + prevCategory.getCategoryId() + " to " + category.getCategoryName();
    }
}
