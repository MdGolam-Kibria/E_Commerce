package com.example.demo.controller;

import com.example.demo.annotation.ApiController;
import com.example.demo.annotation.ValidateData;
import com.example.demo.dto.OrderDto;
import com.example.demo.service.OrderService;
import com.example.demo.util.UrlConstraint;
import com.example.demo.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.OrderManagement.ROOT)
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("permitAll()")
    @ValidateData
    @PostMapping(UrlConstraint.OrderManagement.CREATE + UrlConstraint.ORDER)
    public Response createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult result, HttpServletRequest request) {
        return orderService.create(orderDto, request);
    }
}
