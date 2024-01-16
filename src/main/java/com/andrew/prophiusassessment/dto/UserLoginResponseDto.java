package com.andrew.prophiusassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
public class UserLoginResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String token;

    public UserLoginResponseDto(Long userId, String username, String email, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
    }


}
