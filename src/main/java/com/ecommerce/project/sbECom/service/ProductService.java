package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.model.Product;
import com.ecommerce.project.sbECom.payload.ProductDTO;
import com.ecommerce.project.sbECom.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long id);
}
