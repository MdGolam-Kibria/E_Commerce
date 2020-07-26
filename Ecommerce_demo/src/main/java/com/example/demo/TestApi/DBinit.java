package com.example.demo.TestApi;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class DBinit {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${login.username}")
    private String username;
    @Value("${login.password}")
    private String password;

    @Autowired
    public DBinit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void test() {

        String roleName = "ROLE_ADMIN";
        int roleExistCount = roleRepository.countByName(roleName);
        Role role = null;
        if (roleExistCount == 1) {
            role = roleRepository.findByName(roleName);
        } else {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        User user = userRepository.findByUsernameAndIsActiveTrue(username);
        if (user == null) {
            user = new User();
            user.setEmail("abc@ab.com");
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setRoles(Arrays.asList(role));
        user = userRepository.save(user);
    }
}
