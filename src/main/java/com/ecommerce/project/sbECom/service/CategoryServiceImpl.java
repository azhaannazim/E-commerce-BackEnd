package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.exceptions.APIException;
import com.ecommerce.project.sbECom.exceptions.NoCategoriesFoundException;
import com.ecommerce.project.sbECom.exceptions.ResourceNotFoundException;
import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.payload.CategoryDTO;
import com.ecommerce.project.sbECom.payload.CategoryResponse;
import com.ecommerce.project.sbECom.repositories.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    //private List<Category> list = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> list = categoryRepository.findAll();

        if(list.isEmpty()){
            throw new NoCategoriesFoundException("No category found in the repository");
            //throw new APIException("No category found in the repository");
        }
        List<CategoryDTO> categoryDTOS = list.stream()
                .map(category -> modelMapper.map(category , CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }
    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if(savedCategory != null){
            throw new APIException("category with the name : " + category.getCategoryName() + ", already exist");
        }
        else categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> Cat = categoryRepository.findById(categoryId);
        Category category = Cat
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId ));

        categoryRepository.delete(category);
        return "successfully delete category : " + category.getCategoryName();
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Optional<Category> prevCat = categoryRepository.findById(categoryId);

        Category prevCategory = prevCat
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId ));

        prevCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(prevCategory);
        return "successfully updated category with id : " + prevCategory.getCategoryId() + " to " + category.getCategoryName();
    }
}
