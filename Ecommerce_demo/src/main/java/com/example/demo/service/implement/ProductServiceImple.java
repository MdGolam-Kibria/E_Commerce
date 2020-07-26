package com.example.demo.service.implement;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImple implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private String root = "Product";

    @Autowired
    public ProductServiceImple(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response save(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product = productRepository.save(product);
        if (product != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, root + " Created Successfully", null);
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }


    private List<ProductDto> getProducts(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(product -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDtos.add(productDto);
        });
        return productDtos;
    }
}
