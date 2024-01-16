package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.UserLoginDto;
import com.andrew.prophiusassessment.dto.UserLoginResponseDto;
import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.entity.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface UserService{
    User registerNewUserAccount(UserRegistrationDto userRegistrationDto);

    UserLoginResponseDto loginUser(UserLoginDto loginDto);
}
