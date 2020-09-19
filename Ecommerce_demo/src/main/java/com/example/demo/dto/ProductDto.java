package com.example.demo.dto;

import com.example.demo.model.Categories;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    @NotNull(message = "Quantity is Mandatory")
    @Min(1)
    private int quantity;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Stock is mandatory")
    private int stock;
    private List<CategoriesDto> categories ;
}
