package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.annotation.ValidateData;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @ValidateData
    @PostMapping(UrlConstraint.CustomerManagement.CREATE)
    public Response createUser(@Valid @RequestBody UserDto userDto, BindingResult result, HttpServletRequest request) {
        return userService.createUser(userDto);
    }
}
