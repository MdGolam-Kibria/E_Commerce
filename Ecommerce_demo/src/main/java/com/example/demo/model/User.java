package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "user")
public class User extends BaseModel {
    @JsonProperty("email")
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private List<Role> roles;
}
