package com.andrew.prophiusassessment.response;

import lombok.Data;
@Data
public class ApiResponse<T>  {
    private final String message;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
    }
}
