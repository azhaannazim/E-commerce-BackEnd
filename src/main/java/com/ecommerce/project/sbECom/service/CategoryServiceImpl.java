package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.exceptions.APIException;
import com.ecommerce.project.sbECom.exceptions.NoCategoriesFoundException;
import com.ecommerce.project.sbECom.exceptions.ResourceNotFoundException;
import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.payload.CategoryDTO;
import com.ecommerce.project.sbECom.payload.CategoryResponse;
import com.ecommerce.project.sbECom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    //private List<Category> list = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> list = categoryPage.getContent();

        if(list.isEmpty()){
            throw new NoCategoriesFoundException("No category found in the repository");
            //throw new APIException("No category found in the repository");
        }
        List<CategoryDTO> categoryDTOS = list.stream()
                .map(category -> modelMapper.map(category , CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO , Category.class);
        Category savedCategoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());

        if(savedCategoryFromDb != null){
            throw new APIException("category with the name : " + category.getCategoryName() + ", already exist");
        }
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory , CategoryDTO.class);
        return savedCategoryDTO;

    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> Cat = categoryRepository.findById(categoryId);
        Category category = Cat
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId ));

        categoryRepository.delete(category);


        return modelMapper.map(category , CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Optional<Category> prevCat = categoryRepository.findById(categoryId);

        Category prevCategory = prevCat
                .orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId ));

        Category category = modelMapper.map(categoryDTO , Category.class);
        category.setCategoryId(categoryId);

        categoryRepository.save(category);
        return modelMapper.map(category , CategoryDTO.class);
    }
}
