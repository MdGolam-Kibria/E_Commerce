package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.service.UserService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@ApiController
@RequestMapping(UrlConstraint.CustomerManagement.ROOT)
public class CustomerController {
    private final UserService userService;

    @Autowired
    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(UrlConstraint.CustomerManagement.GET_ALL_CUSTOMER)
    public Response getAllUsers(HttpServletRequest request) {
        return userService.getAllusers();
    }
}
