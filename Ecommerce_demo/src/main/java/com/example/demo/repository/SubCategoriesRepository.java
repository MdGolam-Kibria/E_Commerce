package com.example.demo.repository;

import com.example.demo.model.SubCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubCategoriesRepository extends JpaRepository<SubCategories,Long> {
    SubCategories findByIdAndIsActiveTrue(Long subcategoryId);
    SubCategories findBySubCategoriesNameAndIsActiveTrue(String subCategoriesName);
    //@Query(value = "SELECT r.id FROM User r WHERE r.phone= :phone", nativeQuery = true)
}
