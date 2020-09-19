package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.annotation.IsAdmin;
import com.example.demo.annotation.ValidateData;
import com.example.demo.dto.CategoriesDto;
import com.example.demo.service.ProductService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.ProductManagement.ROOT)
public class CategoryController {
    private final ProductService productService;

    @Autowired
    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @ValidateData
    @PostMapping(UrlConstraint.ProductManagement.CREATE + UrlConstraint.CATEGORIES)
    @IsAdmin
    public Response createCategory(@Valid @RequestBody CategoriesDto categoriesDto, BindingResult result, HttpServletRequest request) {
        return productService.createCategory(categoriesDto);
    }
}
