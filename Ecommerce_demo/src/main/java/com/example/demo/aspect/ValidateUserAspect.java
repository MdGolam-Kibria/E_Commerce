package com.example.demo.aspect;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CheckUserRole;
import com.example.demo.util.UrlConstraint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class ValidateUserAspect {
    @Value("${server.servlet.context-path}")
    private String URL_CONTEXT;
    private Logger LOGGER = LoggerFactory.getLogger(ValidateUserAspect.class);
    private final UserRepository userRepository;
    private final CheckUserRole checkUserRole;

    @Autowired
    public ValidateUserAspect(UserRepository userRepository, CheckUserRole checkUserRole) {
        this.userRepository = userRepository;
        this.checkUserRole = checkUserRole;
    }

    @After(value = "execution(public * com.example.demo.controller.ProductController.*(..)) " +
            "&& ! execution(public * com.example.demo.controller.ProductController.get())")
    public void getMethodData(JoinPoint joinPoint) {

        Object[] signatures = joinPoint.getArgs();
        HttpServletRequest incomingRequest = null;
        for (int i = 0; i < signatures.length; i++) {
            if (signatures[i] instanceof HttpServletRequest) {
                incomingRequest = (HttpServletRequest) signatures[i];
                break;
            }
        }
        String requestedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndIsActiveTrue(requestedUserName);
        if (user != null) {
            if (incomingRequest != null && checkUserRole.getRoleType(user.getRoles()).equals(UrlConstraint.ADMIN)) {
                    LOGGER.info("\nProduct CRUD By  = " + requestedUserName +
                            "\nMethod is = "+joinPoint.getSignature().getName()+
                            "\nRequested url is = "+incomingRequest.getRequestURI());
            }
        }
    }
}
