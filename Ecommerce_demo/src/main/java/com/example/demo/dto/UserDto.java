package com.example.demo.dto;

import com.example.demo.model.Address;
import com.example.demo.model.Phone;
import com.example.demo.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    @NotEmpty(message = "User Name is Mandatory")
    private String username;
    @NotBlank(message = "You Have To Enter The Password")
    @Size(min = 8, max = 20, message = "Password length should be in between 8 to 20")
    @JsonProperty
    private String password;
    @NotBlank(message = "Email field should not be empty")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid Email Pattern")
    private String email;
    @NotBlank(message = "Area is Mandatory")
    private String area;
    @NotEmpty(message = "Phone Number is Mandatory")
    private List<@Valid Phone> phones;
    @NotEmpty(message = "Address is Mandatory")
    private List<@Valid Address> addresses;
    @NotEmpty(message = "Role is Mandatory")
    private List<@Valid Role> roles;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }
}
