package com.example.demo.TestApi;

import com.example.demo.model.Address;
import com.example.demo.model.Phone;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class DBinit {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;
    @Value("${login.username}")
    private String username;
    @Value("${login.password}")
    private String password;

    @Autowired
    public DBinit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PhoneRepository phoneRepository, AddressRepository addressRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    public void runtimeCreateUser() {

        String roleName = "ROLE_ADMIN";
        int roleExistCount = roleRepository.countByNameAndIsActiveTrue(roleName);
        Role role = null;
        if (roleExistCount == 1) {
            role = roleRepository.findByNameAndIsActiveTrue(roleName);
        } else {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        User user = userRepository.findByUsernameAndIsActiveTrue(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail("golamkibria@gmail.com");
            user.setArea("New Market");

            Phone phone = new Phone();
            phone.setPhone("01782683986");
            user.setPhoneList(Arrays.asList(phone));

            Address address = new Address();
            address.setName("51/12, johnson road, 1100. City: Dhaka");
            address = addressRepository.save(address);

            user.setRoles(Arrays.asList(role));
            address.setUsers(Arrays.asList(user));
            user.setAddressList(Arrays.asList(address));
            phone.setUser(user);

            user = userRepository.save(user);
            phone = phoneRepository.save(phone);
        }
    }
}
