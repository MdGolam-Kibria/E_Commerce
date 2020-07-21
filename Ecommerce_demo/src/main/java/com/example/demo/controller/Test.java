package com.example.demo.controller;

import com.example.demo.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;

@ApiController
public class Test {
    @GetMapping("/hello")
    public String get() {
        return "hello spring boot";//this is only fot test
    }
}
