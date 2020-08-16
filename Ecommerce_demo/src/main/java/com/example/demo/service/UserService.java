package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.view.Response;

public interface UserService {
    Response createUser(UserDto userDto);
    User getUserByUserName(String username);
    Response getAllusers();
}
