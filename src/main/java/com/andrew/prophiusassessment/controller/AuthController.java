package com.andrew.prophiusassessment.controller;

import com.andrew.prophiusassessment.dto.UserRegistrationDto;
import com.andrew.prophiusassessment.exceptions.UserAlreadyExistException;
import com.andrew.prophiusassessment.response.ApiResponse;
import com.andrew.prophiusassessment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1/user")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            userService.registerNewUserAccount(registrationDto);
            return new ResponseEntity<>(new ApiResponse<>("User registered successfully"), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping("/user")
    public ResponseEntity<?> getUserDetailsAfterLogin(Authentication authentication) {
        return userService.getUserDetails(authentication.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}
