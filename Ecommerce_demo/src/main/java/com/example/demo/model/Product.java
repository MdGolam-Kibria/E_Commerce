package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "product")
public class Product extends BaseModel {
    private String name;
    private String image;
    private double mainPrice;
    private double discountPrice;
    private String discountNote;
    private String categories;
    private int quantity;
    private String description;
    private int stock;
}
