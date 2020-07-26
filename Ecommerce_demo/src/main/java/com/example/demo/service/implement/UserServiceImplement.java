package com.example.demo.service.implement;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByUsernameAndIsActiveTrue(username);
    }
}
