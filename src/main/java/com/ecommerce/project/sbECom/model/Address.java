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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "street name should be atleast 5 digits")
    private String street;

    @NotBlank
    @Size(min = 5, message = "building name should be atleast 5 digits")
    private String buildingName;

    @NotBlank
    @Size(min = 4, message = "city name should be atleast 5 digits")
    private String city;

    @NotBlank
    @Size(min = 2, message = "state name should be atleast 5 digits")
    private String state;

    @NotBlank
    @Size(min = 5, message = "building name should be atleast 5 digits")
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(String street, String buildingName, String city, String state, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
}
