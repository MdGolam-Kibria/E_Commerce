package com.example.demo.aspect;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CheckUserRole;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Configuration
public class ValidateUserAspect {
    private Logger LOGGER = LoggerFactory.getLogger(ValidateUserAspect.class);
    private final UserRepository userRepository;
    private final CheckUserRole checkUserRole;

    @Autowired
    public ValidateUserAspect(UserRepository userRepository, CheckUserRole checkUserRole) {
        this.userRepository = userRepository;
        this.checkUserRole = checkUserRole;
    }

    @After(value = "execution(public * com.example.demo.controller.ProductController.*(..))" +//will we add for update,delete a product by admin
            "&& args(productDto,bindingResult,request,response)")//for get current user/admin
    public void getMethodData(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, ProductDto productDto) {
        String requestedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndIsActiveTrue(requestedUserName);
        if (user != null) {
            if (checkUserRole.getRoleType(user.getRoles()).equals("ROLE_ADMIN")) {
                LOGGER.info("Product Created By  = " + requestedUserName);
            }
            //will check ROLE_USER when user order a product then logged the username
        }
    }
}
