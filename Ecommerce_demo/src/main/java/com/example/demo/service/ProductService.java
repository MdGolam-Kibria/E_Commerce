package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.view.Response;

public interface ProductService {
    Response save(ProductDto productDto);
    Response update(Long id,ProductDto productDto);
    Response get(Long id);
    Response delete(Long id);
    Response getAll();
}
