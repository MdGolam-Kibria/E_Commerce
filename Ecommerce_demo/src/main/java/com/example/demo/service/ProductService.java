package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.view.Response;

public interface ProductService {
    Response save(ProductDto productDto);
}
