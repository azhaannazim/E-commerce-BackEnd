package com.ecommerce.project.sbECom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cartDTO;
    private ProductDTO productDTO;
    private Integer quantity;
    private Double discount;
    private Double productPrice;

}
