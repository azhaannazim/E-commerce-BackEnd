package com.ecommerce.project.sbECom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 30)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max = 100)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 200)
    @Column(name = "password")
    private String password;

    public User(String userName, String email, String password) {
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    @Setter
    @Getter
    @ManyToMany(cascade = {CascadeType.PERSIST ,CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Setter
    @Getter
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST ,CascadeType.MERGE} ,orphanRemoval = true)
//    @JoinTable(name = "user_address",
//                joinColumns = @JoinColumn(name = "user_id"),
//                inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    //mapping seller to his/her products
    @ToString.Exclude
    @OneToMany(mappedBy = "user",
                cascade = {CascadeType.PERSIST ,CascadeType.MERGE},
                orphanRemoval = true) // if seller is removed then his/her products are also
    private Set<Product> products;

    @ToString.Exclude
    @OneToOne(mappedBy = "user" ,cascade = {CascadeType.PERSIST ,CascadeType.MERGE} ,orphanRemoval = true)
    private Cart cart;
}
