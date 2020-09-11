package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddressDto {
    private Long id;
    //Add validation constraint ge
    @NotEmpty(message = "Address Name is Mandatory")
    private String name;
}
