package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PhoneDto {
    private Long id;
    @NotEmpty(message = "Phone is mandatory")
    @Pattern(regexp = "^(?:\\+?88)?01[135-9]\\d{8}$", message = "invalid mobile number.") @Size(max = 11, message = "digits should be 11")
    private String phone;
}
