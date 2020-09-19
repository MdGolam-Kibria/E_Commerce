package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "subcategories_product")
public class SubCategoriesProduct extends BaseModel {
    private Long subCategoriesId;
    private Long productid;
}
