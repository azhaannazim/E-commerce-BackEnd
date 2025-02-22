package com.ecommerce.project.sbECom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 3, message = "category Name must contains at least 3 characters")
    private String productName;
    private String image;
    @NotBlank
    @Size(min = 5, message = "category Name must contains at least 5 characters")
    @Column(name = "description", length = 1000)
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product" ,cascade = {CascadeType.MERGE ,CascadeType.PERSIST} ,fetch = FetchType.EAGER)
    private List<CartItem> products = new ArrayList<>();

    @Column(name = "returns", nullable = false)
    private int returns = 0;

    public void incrementReturnsCount() {
        this.returns++;
    }

    private double averageRating = 0.0;
    private int ratings = 0;

    public void incrementRatingCount() {
        this.ratings++;
    }

}
