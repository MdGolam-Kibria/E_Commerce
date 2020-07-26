package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.annotation.ValidateData;
import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.ProductManagement.ROOT)
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;

    @Autowired
    public ProductController(ProductService productService,UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @PostMapping(UrlConstraint.ProductManagement.CREATE)
    @ValidateData
    public Response createProduct(@Valid @RequestBody ProductDto productDto, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String requestedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndIsActiveTrue(requestedUserName);
        if (user != null) {
            if (user.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
                return productService.save(productDto);
            }
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.BAD_REQUEST, "Sorry You Can't Add products");
    }
}
