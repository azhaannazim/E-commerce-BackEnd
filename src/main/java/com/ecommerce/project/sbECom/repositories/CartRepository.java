package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart , Long> {
    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.email = :email")
    Cart findCartByEmail(@Param("email") String email);
}
