package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Categories extends BaseModel {
    private String name;
    @ManyToMany(mappedBy = "categoriesList")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
