package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.entity.User;

import java.util.Optional;

public interface UserService{
    void registerNewUserAccount(UserRegistrationDto userRegistrationDto);

//    UserLoginResponseDto loginUser(UserLoginDto loginDto);
    Optional<User> getUserDetails(String email);

    void followUser(Long userId, Long followUserId);
    void unfollowUser(Long userId, Long followUserId);

}
