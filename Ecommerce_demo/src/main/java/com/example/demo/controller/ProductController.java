package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.annotation.IsAdmin;
import com.example.demo.annotation.ValidateData;
import com.example.demo.dto.ProductDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.CheckUserRole;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.ProductManagement.ROOT)
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;
    private final CheckUserRole checkUserRole;

    @Autowired
    public ProductController(ProductService productService, UserRepository userRepository, CheckUserRole checkUserRole) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.checkUserRole = checkUserRole;
    }

    @ValidateData
    @PostMapping(UrlConstraint.ProductManagement.CREATE)
    @IsAdmin
    public Response createProduct(@Valid @RequestBody ProductDto productDto, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String requestedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndIsActiveTrue(requestedUserName);
        if (user != null) {
            if (checkUserRole.getRoleType(user.getRoles()).equals(UrlConstraint.ADMIN)) {///f
                return productService.createProduct(productDto);
            }
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.BAD_REQUEST, "Sorry You do not have permission for this URL");
    }

    @ValidateData
    @PutMapping(UrlConstraint.ProductManagement.UPDATE)
    @IsAdmin
    public Response updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String requestedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndIsActiveTrue(requestedUserName);
        if (user != null) {
            if (checkUserRole.getRoleType(user.getRoles()).equals(UrlConstraint.ADMIN)) {
                return productService.update(id, productDto);
            }
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.BAD_REQUEST, "Sorry You do not have permission for this URL");
    }

    @PreAuthorize("permitAll()")
    @GetMapping(UrlConstraint.ProductManagement.GET)
    public Response getProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        return productService.get(id);
    }

    @DeleteMapping(UrlConstraint.ProductManagement.DELETE)
    @IsAdmin
    public Response deleteProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        String requestedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndIsActiveTrue(requestedUserName);
        if (user != null) {
            if (checkUserRole.getRoleType(user.getRoles()).equals(UrlConstraint.ADMIN)) {
                return productService.delete(id);
            }
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.BAD_REQUEST, "Sorry You do not have permission for this URL");
    }

    @PreAuthorize("permitAll()")
    @GetMapping(UrlConstraint.ProductManagement.GET_ALL)
    public Response getAllProducts(HttpServletRequest request) {

        return productService.getAll();
    }


    @PreAuthorize("permitAll()")
    @GetMapping(UrlConstraint.ProductManagement.GET_ALL + UrlConstraint.CATEGORIES)
    public Response getAllCategories(HttpServletRequest request) {
        return productService.getAllCategories();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(UrlConstraint.CATEGORIES + UrlConstraint.ProductManagement.GET)
    public Response getAllProductsByCategoriesId(@PathVariable("id") Long id, HttpServletRequest request) {
        return productService.getProductsByCategoryId(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(UrlConstraint.SUB_CATEGORIES + UrlConstraint.ProductManagement.GET)
    public Response getAllSubCategoriesByCategoriesId(@PathVariable("id") Long id, HttpServletRequest request) {
        return productService.getSubCategoriesCategoryId(id);
    }
}
