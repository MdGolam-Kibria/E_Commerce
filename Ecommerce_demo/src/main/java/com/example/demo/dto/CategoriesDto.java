package com.example.demo.dto;

import com.example.demo.model.SubCategories;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CategoriesDto {

    private Long id;
    @NotBlank(message = "categoryName is mandatory")
    private String categoryName;
    private List<SubCategories> subCategoriesList;
}
