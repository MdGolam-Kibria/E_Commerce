package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "product")
public class Product extends BaseModel {
    private String name;
    private String image;
    private double mainPrice;
    private double discountPrice;
    private String discountNote;
    private int quantity;
    private String description;
    private int stock;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private List<Categories> categoriesList;

}
