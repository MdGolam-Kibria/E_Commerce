package com.example.demo.service;

import com.example.demo.dto.LoginDto;
import com.example.demo.view.Response;

public interface AuthService {
    Response login(LoginDto loginDto);
}
