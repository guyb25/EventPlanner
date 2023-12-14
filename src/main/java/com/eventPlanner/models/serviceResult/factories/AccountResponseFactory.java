package com.eventPlanner.models.serviceResult.factories;

import com.eventPlanner.models.serviceResult.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountResponseFactory {
    public ServiceResult usernameTaken() {
        return new ServiceResult("Username is already taken", HttpStatus.CONFLICT);
    }

    public ServiceResult emailTaken() {
        return new ServiceResult("Email is already taken", HttpStatus.CONFLICT);
    }

    public ServiceResult userCreated() {
        return new ServiceResult("User created successfully", HttpStatus.CREATED);
    }

    public ServiceResult wrongUsernameOrPassword() {
        return new ServiceResult("Wrong username or password", HttpStatus.UNAUTHORIZED);
    }

    public ServiceResult userNotFound(String msg) {
        return new ServiceResult(msg, HttpStatus.NOT_FOUND);
    }

    public ServiceResult userDeleted() {
        return new ServiceResult("User deleted", HttpStatus.OK);
    }
}
