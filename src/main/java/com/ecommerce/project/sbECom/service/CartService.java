package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);
}
