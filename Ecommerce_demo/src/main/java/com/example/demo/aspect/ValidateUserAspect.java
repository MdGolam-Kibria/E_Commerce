package com.example.demo.aspect;

import com.example.demo.dto.ProductDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Configuration
public class ValidateUserAspect {
    private Logger LOGGER = LoggerFactory.getLogger(ValidateUserAspect.class);

    @After(value = "execution(public * com.example.demo.controller.ProductController.*(..))" +
            "&& args(productDto,bindingResult,request,response)")//for get current user/admin
    public void getMethodData(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, ProductDto productDto) {
        LOGGER.info("Product Created By  = " + SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
