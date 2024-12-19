package com.chat.MyChatApp.service;

import com.chat.MyChatApp.entity.PasswordResetToken;
import com.chat.MyChatApp.entity.User;
import com.chat.MyChatApp.repository.PasswordResetTokenRepository;
import com.chat.MyChatApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordImpl implements ForgotPassword {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public ForgotPasswordImpl(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public String generateResetToken(String email) {
        String token="";
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not exists"));
         token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryTime(LocalDateTime.now().plusMinutes(30));

        passwordResetTokenRepository.save(passwordResetToken);

        return token;
    }
}
