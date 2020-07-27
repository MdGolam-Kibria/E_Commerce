package com.example.demo.controller;

import com.example.demo.annotation.*;
import com.example.demo.dto.LoginDto;
import com.example.demo.service.AuthService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.AuthManagement.ROOT)
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(UrlConstraint.AuthManagement.LOGIN)
    @ValidateData
    public Response login(@RequestBody @Valid LoginDto loginDto, BindingResult result, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return authService.login(loginDto);
    }
}
