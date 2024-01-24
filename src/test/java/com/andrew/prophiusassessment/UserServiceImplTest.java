package com.andrew.prophiusassessment;

import com.andrew.prophiusassessment.entity.User;
import com.andrew.prophiusassessment.repositories.UserRepository;
import com.andrew.prophiusassessment.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void followUserSuccessful(){
        Long userId = 1L;
        Long followUserId = 2L;

        User user = new User();
        user.setId(userId);

        User userToFollow = new User();
        userToFollow.setId(followUserId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(followUserId)).thenReturn(Optional.of(userToFollow));

        userService.followUser(userId, followUserId);

        verify(userRepository).findById(userId);
        verify(userRepository).findById(followUserId);
        verify(userRepository).save(user);

        assertTrue(user.getFollowing().contains(userToFollow));
        assertTrue(userToFollow.getFollowers().contains(user));

    }

    @Test
    void unfollowUserSuccessful(){
        Long userId = 1L;
        Long followUserId = 2L;

        User user = new User();
        user.setId(userId);

        User userToUnfollow = new User();
        userToUnfollow.setId(followUserId);

        user.follow(userToUnfollow); // Initially, user follows userToUnfollow

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(followUserId)).thenReturn(Optional.of(userToUnfollow));

        userService.unfollowUser(userId, followUserId);

        verify(userRepository).findById(userId);
        verify(userRepository).findById(followUserId);
        verify(userRepository).save(user);

        assertFalse(user.getFollowing().contains(userToUnfollow));
        assertFalse(userToUnfollow.getFollowers().contains(user));

    }
}
