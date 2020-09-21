package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.Column;

@Data
public class SubCategoriesDto {
    private Long id;
    @Column(name = "subCategoriesName")
    private String subCategoriesName;
}
