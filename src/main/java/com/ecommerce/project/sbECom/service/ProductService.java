package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.Product;
import com.ecommerce.project.sbECom.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
