package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Name is Mandatory")
    private String name;
    @NotEmpty(message = "Image is mandatory")
    private String image;
    @NotNull(message = "price is mandatory")
    private Double mainPrice;
    private Double discountPrice;
    private String discountNote;
    @NotEmpty(message = "Categories is Mandatory")
    private String categories;
    @NotNull(message = "Quantity is Mandatory")
    private int quantity;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Stock is mandatory")
    private int stock;
}
