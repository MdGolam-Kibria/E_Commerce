package com.example.demo.service.implement;

import com.example.demo.annotation.IsAdmin;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImplement(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsernameAndIsActiveTrue(username);
    }

    @Override
    public Response getAllusers() {
        List<User> users = userRepository.findAllByIsActiveTrue();
        List<UserDto> userDtos = this.getUsers(users);
        int numberOfRow = userRepository.countAllByIsActiveTrue();
        return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "Users Retrieved Successfully", userDtos, users.size(), numberOfRow);

    }

    @Override
    public Response createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        if (user != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "User Created Successfully", user.getUsername());
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    private List<UserDto> getUsers(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDtos.add(userDto);
        });
        return userDtos;
    }
}
