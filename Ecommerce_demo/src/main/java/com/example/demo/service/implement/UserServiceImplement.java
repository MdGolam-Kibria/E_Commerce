package com.example.demo.service.implement;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Address;
import com.example.demo.model.Phone;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.RoleConstraint;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("userService")
public class UserServiceImplement implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImplement.class.getName());
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImplement(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, PhoneRepository phoneRepository, AddressRepository addressRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Response createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        List<Phone> phoneList = user.getPhoneList();
        user.setAddressList(addressRepository.saveAll(user.getAddressList()));
        int roleCustomerCount = roleRepository.countByNameAndIsActiveTrue(RoleConstraint.ROLE_CUSTOMER.name());//Is this Exist?
        Role role;
        if (roleCustomerCount == 0) {
            role = new Role();
            role.setName(RoleConstraint.ROLE_CUSTOMER.name());//get value from enum
            role = roleRepository.save(role);
        } else {
            role = roleRepository.findByNameAndIsActiveTrue(RoleConstraint.ROLE_CUSTOMER.name());
        }
        user.setRoles(Collections.singletonList(role));
        user = userRepository.save(user);
        User finalUser = user;
        phoneList.forEach(phone -> {
            phone.setUser(finalUser);
            phone = phoneRepository.save(phone);
        });
        if (user != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "User Created Successfully", user.getUsername());
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
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
