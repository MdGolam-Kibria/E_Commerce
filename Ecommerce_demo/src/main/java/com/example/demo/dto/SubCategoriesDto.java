package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubCategoriesDto {
    private Long id;
    @NotBlank(message = "SubCategoriesName is Mandatory")
    private String SubCategoriesName;
}
