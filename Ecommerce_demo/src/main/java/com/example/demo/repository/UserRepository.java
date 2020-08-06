package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndIsActiveTrue(String username);

    User findByIdAndIsActiveTrue(Long id);

    User findByEmailAndIsActiveTrue(String email);
    int countAllByIsActiveTrue();
    List<User> findAllByIsActiveTrue();
}
