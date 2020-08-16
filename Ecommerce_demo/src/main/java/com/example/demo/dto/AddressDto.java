package com.example.demo.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    //Add validation constraint ge
    private String name;
}
