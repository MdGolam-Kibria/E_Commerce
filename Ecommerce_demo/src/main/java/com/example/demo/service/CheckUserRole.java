package com.example.demo.service;

import com.example.demo.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("checkUserRole")
public class CheckUserRole {
    public String getRoleType(List<Role> userRoles) {
        for (int i = 0; i < userRoles.size(); i++) {
            if (userRoles.get(i).getName().equals("ROLE_ADMIN")) {
                return userRoles.get(i).getName();
            } else if (userRoles.get(i).getName().equals("ROLE_USER")) {
                return userRoles.get(i).getName();
            }
        }
        return "NoUser";
    }
}
