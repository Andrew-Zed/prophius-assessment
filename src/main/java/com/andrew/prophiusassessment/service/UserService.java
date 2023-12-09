package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.UserLoginDto;
import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.entity.User;

public interface UserService{
    User registerNewUserAccount(UserRegistrationDto userRegistrationDto);

    User loginUser(UserLoginDto loginDto);
}
