package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class OrderProduct extends BaseModel {
    private Long productId;
    private Long orderId;
    private int productQuantity;
}
