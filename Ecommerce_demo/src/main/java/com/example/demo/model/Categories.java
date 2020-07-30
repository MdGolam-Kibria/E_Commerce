package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Categories extends BaseModel {
    private String categoryName;//new
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_sub_categories", joinColumns = @JoinColumn(name = "categories_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id"))
    @ToString.Exclude
    private List<SubCategories> subCategoriesList;

    @ManyToMany(mappedBy = "categoriesList")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
