package com.example.demo.service;

import com.example.demo.dto.LoginDto;
import com.example.demo.view.Response;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    Response login(LoginDto loginDto, HttpServletRequest request);
}
