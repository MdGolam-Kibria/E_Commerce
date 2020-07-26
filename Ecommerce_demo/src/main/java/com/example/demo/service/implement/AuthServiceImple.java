package com.example.demo.service.implement;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.filter.JwtTokenProvider;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImple implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImple(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Response login(LoginDto loginDto) {
        User user = userRepository.findByUsernameAndIsActiveTrue(loginDto.getUsername());
        if (user==null){
            return ResponseBuilder.getFailureResponce(HttpStatus.UNAUTHORIZED,"Invalid Email or password");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
        if (authentication.isAuthenticated()){
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setToken(jwtTokenProvider.generateToken(authentication));
            loginResponseDto.setUsername(user.getUsername());
            return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "Logged In Success", loginResponseDto);
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.BAD_REQUEST, "Invalid Email or password");
    }
}
