package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Address extends BaseModel {
//    @NotEmpty(message = "Address is Mandatory")
//    @Size(min = 8,message = "length should be 8 or more")
    private String name;

    @ManyToMany(mappedBy = "addressList")
    @JsonIgnore
    private List<User> users = new ArrayList<>();
}
