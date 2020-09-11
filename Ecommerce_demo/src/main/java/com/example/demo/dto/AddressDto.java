package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AddressDto {
    private Long id;
    //Add validation constraint ge
    @NotBlank(message = "Address Name is Mandatory")
    @Size(min = 2, max = 50, message = "Address Name is Mandatory")
    @Length(min = 4,max = 50,message = "Address Name is Mandatory")
    private String name;
}
