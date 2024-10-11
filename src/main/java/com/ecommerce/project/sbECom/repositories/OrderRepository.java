package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order , Long> {
}
