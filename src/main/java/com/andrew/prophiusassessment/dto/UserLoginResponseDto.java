package com.andrew.prophiusassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
public class UserLoginResponseDto {
    private String username;
    private String email;
    private String token;

    public UserLoginResponseDto(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }


}
