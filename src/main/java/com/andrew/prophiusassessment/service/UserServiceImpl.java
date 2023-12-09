package com.andrew.prophiusassessment.service;

import com.andrew.prophiusassessment.dto.UserLoginDto;
import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.exceptions.UserAlreadyExistException;
import com.andrew.prophiusassessment.exceptions.UserNotFoundException;
import com.andrew.prophiusassessment.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository /**PasswordEncoder passwordEncoder*/) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDto registrationDto) {
//        if (emailExists(registrationDto.getEmail())) {
//            throw new UserAlreadyExistException("There is an account with that email address: " + registrationDto.getEmail());
//        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("A user with the email " + registrationDto.getEmail() + " already exists.");
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail().toLowerCase());
        user.setPassword(registrationDto.getPassword());

        return userRepository.save(user);
    }

    public User loginUser(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginDto.getEmail()));

        if (user.getPassword().equals(loginDto.getPassword())) {
            return user;
        } else {
            throw new UserNotFoundException("Invalid credentials provided.");
        }
    }

    private boolean emailExists(String email) {
       return userRepository.findByEmail(email).isPresent();
    }
}
