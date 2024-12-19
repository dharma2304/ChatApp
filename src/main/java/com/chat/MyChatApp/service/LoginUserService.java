package com.chat.MyChatApp.service;

import com.chat.MyChatApp.dto.LoginDto;
import com.chat.MyChatApp.entity.User;

public interface LoginUserService {

  public User loginUser(LoginDto loginDto) throws Exception;
}
