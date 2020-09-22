package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.annotation.IsAdmin;
import com.example.demo.annotation.ValidateData;
import com.example.demo.dto.SubCategoriesDto;
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
public class SubCategoryController {
    private final ProductService productService;

    @Autowired
    public SubCategoryController(ProductService productService) {
        this.productService = productService;
    }


    @IsAdmin
    @PostMapping(UrlConstraint.ProductManagement.CREATE + UrlConstraint.SUB_CATEGORIES)
    @ValidateData
    public Response createSubCategories(@RequestBody @Valid SubCategoriesDto subCategoriesDto, BindingResult result, HttpServletRequest request) {
        return productService.createSubCategory(subCategoriesDto);
//        throw new ApiRequestException("");
    }

}
