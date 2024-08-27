package com.ecommerce.project.sbECom.controller;

import com.ecommerce.project.sbECom.payload.CartDTO;
import com.ecommerce.project.sbECom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    private ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId ,quantity);
        return new ResponseEntity<>(cartDTO , HttpStatus.CREATED);
    }
}
