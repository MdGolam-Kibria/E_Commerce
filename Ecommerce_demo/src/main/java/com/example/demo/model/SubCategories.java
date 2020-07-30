package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class SubCategories extends BaseModel {

    private String SubCategoriesName;
    @ManyToMany(mappedBy = "subCategoriesList")
    @JsonIgnore
    private List<Categories> categoriesList;
}
