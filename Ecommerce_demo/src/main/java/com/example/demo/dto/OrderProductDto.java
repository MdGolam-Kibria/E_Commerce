package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderProductDto {
    @NotNull(message = "ProductId is mandatory")
    private Long productId;
    @Min(value = 1, message = "productQuantity must be equal or greater than 1")
    private int productQuantity;
}
