package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    int countAllByIsActiveTrue();
    Product findByNameAndIsActiveTrue(String name);
    Product findByIdAndIsActiveTrue(Long id);
    List<Product> findAllByIsActiveTrue();
}
