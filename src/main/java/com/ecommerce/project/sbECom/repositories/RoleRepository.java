package com.ecommerce.project.sbECom.repositories;

import com.ecommerce.project.sbECom.model.AppRole;
import com.ecommerce.project.sbECom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role , Integer> {
    Optional<Role> findByRoleName(AppRole appRole);
}
