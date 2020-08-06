package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "customer_address")
public class Address extends BaseModel {
    private String address;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
