package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.exceptions.APIException;
import com.ecommerce.project.sbECom.exceptions.ResourceNotFoundException;
import com.ecommerce.project.sbECom.model.Cart;
import com.ecommerce.project.sbECom.model.CartItem;
import com.ecommerce.project.sbECom.model.Product;
import com.ecommerce.project.sbECom.payload.CartDTO;
import com.ecommerce.project.sbECom.payload.ProductDTO;
import com.ecommerce.project.sbECom.repositories.CartItemRepository;
import com.ecommerce.project.sbECom.repositories.CartRepository;
import com.ecommerce.project.sbECom.repositories.ProductRepository;
import com.ecommerce.project.sbECom.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        //find cart or create it
        Cart cart = createCart();
        //find products details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" ,"productId" ,productId));
        // perform validations
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId() ,productId);
        if(cartItem != null){
            throw new APIException("Product" + product.getProductName() + "already exists int the cart");
        }
        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + " is not available");
        }
        if(product.getQuantity() < quantity){
            throw new APIException("please make an order of the " + product.getProductName() +
                    " less than or equal to the quantity" + product.getQuantity());
        }
        //create cart items
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        //save cart item
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity()); //we will reduce it the order is placed
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart ,CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream()
                .map(item ->{
                    ProductDTO map = modelMapper.map(item.getProduct() ,ProductDTO.class);
                    map.setQuantity(item.getQuantity());
                    return map;
                });
        cartDTO.setProductDTOS(productDTOStream.toList());
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if(carts.isEmpty()){
            throw new APIException("No cart exists");
        }

        List<CartDTO> cartDTOS = carts.stream()
                .map(cart -> {
                    CartDTO cartDTO = modelMapper.map(cart ,CartDTO.class);
                    List<ProductDTO> productDTOS = cart.getCartItems().stream()
                            .map(p -> modelMapper.map(p.getProduct() ,ProductDTO.class))
                            .toList();
                    cartDTO.setProductDTOS(productDTOS);
                    return cartDTO;
                }).toList();

        return cartDTOS;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null){
            return userCart;
        }
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
       // cart.setCartItems(new ArrayList<>());
        cart.setUser(authUtil.loggedInUser());

        return cartRepository.save(cart);

    }
}
