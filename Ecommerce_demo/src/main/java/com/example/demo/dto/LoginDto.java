package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "UserName Mandatory")
    private String username;
    @NotBlank(message = "Password Mandatory")
    private String password;
}
