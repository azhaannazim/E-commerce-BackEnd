package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {
}
