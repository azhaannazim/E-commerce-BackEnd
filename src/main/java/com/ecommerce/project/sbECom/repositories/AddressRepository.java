package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address , Long> {
}
