package com.chat.MyChatApp.controller;

import com.chat.MyChatApp.dto.LoginDto;
import com.chat.MyChatApp.dto.UserSignupDto;
import com.chat.MyChatApp.entity.User;
import com.chat.MyChatApp.service.ForgotPasswordImpl;
import com.chat.MyChatApp.service.LoginUserServiceImpl;
import com.chat.MyChatApp.service.MailService;
import com.chat.MyChatApp.service.UserSignUpServiceImpl;
import com.chat.MyChatApp.utils.JwtUtils;
import com.chat.MyChatApp.utils.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserSignUpServiceImpl userSignUpServiceImpl;
    private final LoginUserServiceImpl loginUserServiceImpl;
    private final JwtUtils jwtUtils;
    private final ForgotPasswordImpl forgotPasswordImpl;
    private final MailService emailService;

    public AuthenticationController(UserSignUpServiceImpl userSignUpServiceImpl, LoginUserServiceImpl loginUserServiceImpl, JwtUtils jwtUtils, ForgotPasswordImpl forgotPasswordImpl, MailService emailService){
        this.userSignUpServiceImpl = userSignUpServiceImpl;
        this.loginUserServiceImpl = loginUserServiceImpl;
        this.jwtUtils = jwtUtils;
        this.forgotPasswordImpl = forgotPasswordImpl;
        this.emailService = emailService;
    }

    @PostMapping("/signupUser")
    public User signupUser(@RequestBody UserSignupDto userSignupDto) throws Exception {
        User user = userSignUpServiceImpl.registerUser(userSignupDto);
        return user;
    }

    @PostMapping("/login")
    public LoginResponse  loginUser(@RequestBody LoginDto loginDto, HttpServletResponse response) throws Exception {
        User authenticatedUser = loginUserServiceImpl.loginUser(loginDto);
        String jwtToken = jwtUtils.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpireTime(jwtUtils.getEXPIRATION_TIME());

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return loginResponse ;
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email){
        String resetToken = forgotPasswordImpl.generateResetToken(email);
        emailService.emailResetToken(resetToken, email);
       return "Reset token sent succesfully to email address";
    }

}
