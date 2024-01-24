package com.andrew.prophiusassessment.controller;

import com.andrew.prophiusassessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/follow/{followUserId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId, @PathVariable Long followUserId) {
        userService.followUser(userId, followUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/unfollow/{followUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, @PathVariable Long followUserId) {
        userService.unfollowUser(userId, followUserId);
        return ResponseEntity.ok().build();
    }

}
