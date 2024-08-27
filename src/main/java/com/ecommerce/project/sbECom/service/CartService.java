package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.payload.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();
}
