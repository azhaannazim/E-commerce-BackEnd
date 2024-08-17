package com.ecommerce.project.sbECom.payload;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank
    @Size(min = 3, message = "category Name must contains at least 3 characters")
    private String productName;
    private String image;
    @NotBlank
    @Size(min = 5, message = "category Name must contains at least 5 characters")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

}
