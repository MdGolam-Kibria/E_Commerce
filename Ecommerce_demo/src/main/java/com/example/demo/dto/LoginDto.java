package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginDto {
    @NotBlank(message = "username field should not be empty")
    @Size(min = 4,max = 20,message = "length should be in between 4 to 20")
    private String username;
    @NotBlank(message = "Password Mandatory")
    @Size(min = 8, max = 20, message = "length should be in between 8 to 20")
    private String password;
}
