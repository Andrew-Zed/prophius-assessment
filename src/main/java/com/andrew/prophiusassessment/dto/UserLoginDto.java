package com.andrew.prophiusassessment.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
}
