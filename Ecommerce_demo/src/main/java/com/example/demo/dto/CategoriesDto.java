package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesDto {

    private Long id;
    private String categoryName;
    private List<SubCategoriesDto> subCategoriesList;
}
