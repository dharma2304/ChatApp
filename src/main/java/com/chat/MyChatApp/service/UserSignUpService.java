package com.chat.MyChatApp.service;

import com.chat.MyChatApp.dto.UserSignupDto;
import com.chat.MyChatApp.entity.User;

public interface UserSignUpService {
   User registerUser(UserSignupDto userSignupDto) throws Exception;
}
