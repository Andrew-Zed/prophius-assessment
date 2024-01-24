package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.exceptions.UserAlreadyExistException;
import com.andrew.prophiusassessment.exceptions.UserNotFoundException;
import com.andrew.prophiusassessment.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerNewUserAccount(UserRegistrationDto registrationDto) {
        if (emailExists(registrationDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + registrationDto.getEmail());
        }
//        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
//            throw new UserAlreadyExistException("A user with the email " + registrationDto.getEmail() + " already exists.");
//        }
        String hashPwd = passwordEncoder.encode(registrationDto.getPassword());
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setRole("USER");
        user.setEmail(registrationDto.getEmail().toLowerCase());
        user.setPassword(hashPwd);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void followUser(Long userId, Long followUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User userToFollow = userRepository.findById(followUserId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));
        user.follow(userToFollow);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void unfollowUser(Long userId, Long followUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User userToUnfollow = userRepository.findById(followUserId)
                .orElseThrow(() -> new RuntimeException(""));
        user.unfollow(userToUnfollow);
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserDetails(String email) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email)));
    }

    private boolean emailExists(String email) {
       return userRepository.findByEmail(email).isPresent();
//        return userRepository.findByEmail(email).size() > 0;
    }


}
