package com.example.demo.service;

import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.SubCategoriesDto;
import com.example.demo.view.Response;

public interface ProductService {
    Response createSubCategory(SubCategoriesDto subCategoriesDto);
    Response createCategory(CategoriesDto categoriesDto);
    Response createProduct(ProductDto productDto);
    Response update(Long id,ProductDto productDto);
    Response get(Long id);
    Response delete(Long id);
    Response getAll();
    Response getAllCategories();
    Response getAllSubCategories();
    Response getProductsByCategoryId(Long categoryId);
    Response getSubCategoriesCategoryId(Long categoryId);
}
