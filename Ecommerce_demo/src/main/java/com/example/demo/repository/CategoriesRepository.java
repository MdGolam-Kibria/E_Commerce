package com.example.demo.repository;

import com.example.demo.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    //int countByCategoryNameAndIsActiveTrue(String name);
    List<Categories> findAllByIsActiveTrue();
    int countAllByIsActiveTrue();
    Categories findByCategoryNameAndIsActiveTrue(String categoryName);
    Categories findByIdAndIsActiveTrue(Long categoryId);

}
