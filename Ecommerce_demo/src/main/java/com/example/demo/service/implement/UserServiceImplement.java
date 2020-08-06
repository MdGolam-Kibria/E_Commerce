package com.example.demo.service.implement;

import com.example.demo.annotation.IsAdmin;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImplement(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsernameAndIsActiveTrue(username);
    }

    @IsAdmin
    @Override
    public Response getAllusers() {
        List<User> users = userRepository.findAllByIsActiveTrue();
        List<UserDto> userDtos = this.getUsers(users);
        int numberOfRow = userRepository.countAllByIsActiveTrue();
        return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "Users Retrieved Successfully", userDtos, users.size(), numberOfRow);

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
