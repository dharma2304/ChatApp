package com.chat.MyChatApp.service;

import com.chat.MyChatApp.dto.UserSignupDto;
import com.chat.MyChatApp.entity.User;
import com.chat.MyChatApp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSignUpServiceImpl implements UserSignUpService {
    private final  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserSignupDto userSignupDto) throws Exception {

        if(userRepository.existsByEmail(userSignupDto.email())){
            throw new Exception("User Already Exists");
        } else{
            String hashedPassword = passwordEncoder.encode(userSignupDto.password());
            User user = new User();
            user.setFullName(userSignupDto.fullName());
            user.setEmail(userSignupDto.email());
            user.setPassword(hashedPassword);
            return userRepository.save(user);
        }
    }
}
