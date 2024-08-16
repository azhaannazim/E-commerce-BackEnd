package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.exceptions.ResourceNotFoundException;
import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.model.Product;
import com.ecommerce.project.sbECom.payload.ProductDTO;
import com.ecommerce.project.sbECom.repositories.CategoryRepository;
import com.ecommerce.project.sbECom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" ,"categoryId" ,categoryId));
        product.setCategory(category);
        product.setImage("default.png");
        double specialPrice = product.getPrice() - (product.getPrice() * (product.getDiscount() * .01));
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct , ProductDTO.class);
    }
}
