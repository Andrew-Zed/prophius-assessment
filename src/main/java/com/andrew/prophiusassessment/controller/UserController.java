package com.andrew.prophiusassessment.controller;

import com.andrew.prophiusassessment.dto.UserLoginDto;
import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.exceptions.UserAlreadyExistException;
import com.andrew.prophiusassessment.exceptions.UserNotFoundException;
import com.andrew.prophiusassessment.response.ApiResponse;
import com.andrew.prophiusassessment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            userService.registerNewUserAccount(registrationDto);
            return new ResponseEntity<>(new ApiResponse("User registered successfully"), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
        // Include more exception handling as necessary
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody UserLoginDto loginDto) {
        try {
            userService.loginUser(loginDto);
            return ResponseEntity.ok(new ApiResponse("Login successful"));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

}
