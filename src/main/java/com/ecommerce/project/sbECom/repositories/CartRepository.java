package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart , Long> {
    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.email = :email")
    Cart findCartByEmail(@Param("email") String email);

    @Query("SELECT c FROM Cart c where c.user.email = ?1 AND c.id = ?2")
    Cart findCartByEmailAndCartId(String email, Long cartId);

    @Query("SELECT DISTINCT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = :productId")
    List<Cart> findCartsByProductId(@Param("productId") Long productId);
}
