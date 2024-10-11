package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , Long> {
}
