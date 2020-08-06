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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class CustomerTest {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerTest(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PhoneRepository phoneRepository, AddressRepository addressRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    public void Test() {
        String roleName = "ROLE_CUSTOMER";
        int roleExistCount = roleRepository.countByNameAndIsActiveTrue(roleName);
        Role role = null;
        if (roleExistCount == 1) {
            role = roleRepository.findByNameAndIsActiveTrue(roleName);
        } else {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        User user = userRepository.findByEmailAndIsActiveTrue("kibria@gmail.com");
        Phone phone = new Phone();
        Address address = new Address();
//        if (user==null){
        user = new User();
        user.setUsername("kibria");
        user.setPassword(passwordEncoder.encode("413152413152"));
        user.setEmail("kibria@gmail.com");
        user.setArea("Dhanmondi");

        phone.setPhone("01531921892");
        user.setPhones(Arrays.asList(phone));

        address.setAddress("51/12, johnson road, 1100. City: Dhaka");
        user.setAddresses(Arrays.asList(address));
        //}
        user.setRoles(Arrays.asList(role));
        user = userRepository.save(user);
        phone.setUser(user);
        address.setUser(user);

        phone = phoneRepository.save(phone);
        address = addressRepository.save(address);
    }
}
