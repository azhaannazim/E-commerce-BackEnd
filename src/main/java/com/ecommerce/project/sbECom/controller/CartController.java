package com.ecommerce.project.sbECom.controller;

import com.ecommerce.project.sbECom.payload.CartDTO;
import com.ecommerce.project.sbECom.payload.CartItemDTO;
import com.ecommerce.project.sbECom.payload.ProductDTO;
import com.ecommerce.project.sbECom.repositories.CartRepository;
import com.ecommerce.project.sbECom.service.CartService;
import com.ecommerce.project.sbECom.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/cart/create")
    public ResponseEntity<String> createOrUpdateCart(@RequestBody List<CartItemDTO> cartItems){
        String response = cartService.createOrUpdateCartWithItems(cartItems);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    private ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId ,quantity);
        return new ResponseEntity<>(cartDTO , HttpStatus.CREATED);
    }
    @GetMapping("/carts")
    private ResponseEntity<?> getCarts(){
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOS ,HttpStatus.FOUND);
    }
    @GetMapping("/carts/users/cart")
    private ResponseEntity<?> getCartById(){
        String email = authUtil.loggedInEmail();
        Long cartId = cartRepository.findCartByEmail(email).getCartId();
        CartDTO cartDTO = cartService.getCart(email ,cartId); //for future useCase i might add multiple carts to a single user
        return new ResponseEntity<>(cartDTO ,HttpStatus.OK);
    }
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
