package com.example.demo.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CategoriesDto {

    private Long id;
    @NotBlank(message = "CategoryName is Mandatory")
    private String categoryName;
    private List<@Valid SubCategoriesDto> subCategoriesList;
}
