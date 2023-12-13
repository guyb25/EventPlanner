package com.eventPlanner.models.serviceResult;

import org.springframework.http.HttpStatus;

public enum ServiceResult {
    USERNAME_TAKEN("Username is already taken", HttpStatus.CONFLICT),
    EMAIL_TAKEN("Email is already taken", HttpStatus.CONFLICT),
    USER_CREATED("User created successfully", HttpStatus.CREATED);

    private final String message;
    private final HttpStatus httpStatus;

    ServiceResult(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
