package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.view.Response;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    Response create(OrderDto orderDto, HttpServletRequest request);
}
