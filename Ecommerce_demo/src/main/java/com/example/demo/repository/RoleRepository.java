package com.example.demo.repository;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    int countByNameAndIsActiveTrue(String name);

    //Role findByName(String roleName);
    Role findByNameAndIsActiveTrue(String roleName);
}
