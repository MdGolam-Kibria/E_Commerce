package com.example.demo.dto;

import com.example.demo.model.Address;
import com.example.demo.model.Phone;
import com.example.demo.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    @NotEmpty(message = "User Name is Mandatory")
    private String username;
    @NotBlank
    @Size(min = 8, max = 20, message = "Password length should be in between 8 to 20")
    @JsonIgnore
    private String password;
    @NotBlank(message = "Email field should not be empty")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid Email Pattern")
    private String email;
    @NotBlank(message = "Area is Mandatory")
    private String area;
    //@Pattern(regexp = "^(?:\\+?88)?01[135-9]\\d{8}$", message = "invalid mobile number.")
    //@Size(max = 11, message = "digits should be 11")
    @NotNull(message = "Phone Number is Mandatory")
    private List<Phone> phones;
    @NotNull(message = "Address is Mandatory")
    private List<Address> addresses;
    @NotNull(message = "Role is Mandatory")
    private List<Role> roles;
}
