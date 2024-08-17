package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.payload.ProductDTO;
import com.ecommerce.project.sbECom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long id);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId , ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
