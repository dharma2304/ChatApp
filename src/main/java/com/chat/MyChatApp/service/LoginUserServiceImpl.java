package com.chat.MyChatApp.service;

import com.chat.MyChatApp.dto.LoginDto;
import com.chat.MyChatApp.entity.User;
import com.chat.MyChatApp.repository.UserRepository;
import com.chat.MyChatApp.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserServiceImpl implements LoginUserService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginUserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User loginUser(LoginDto loginDto) throws Exception {
        String email = loginDto.email();
        String password = loginDto.password();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return userRepository.findByEmail(email).orElseThrow(() -> new Exception("Invalid username or password"));
    }

}
