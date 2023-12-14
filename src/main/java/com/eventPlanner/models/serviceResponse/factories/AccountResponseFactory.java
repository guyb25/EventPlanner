package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.serviceResponse.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountResponseFactory {
    public ServiceResponse usernameTaken() {
        return new ServiceResponse("Username is already taken", HttpStatus.CONFLICT);
    }

    public ServiceResponse emailTaken() {
        return new ServiceResponse("Email is already taken", HttpStatus.CONFLICT);
    }

    public ServiceResponse userCreated() {
        return new ServiceResponse("User created successfully", HttpStatus.CREATED);
    }

    public ServiceResponse wrongUsernameOrPassword() {
        return new ServiceResponse("Wrong username or password", HttpStatus.UNAUTHORIZED);
    }

    public ServiceResponse userNotFound(String msg) {
        return new ServiceResponse(msg, HttpStatus.NOT_FOUND);
    }

    public ServiceResponse userDeleted() {
        return new ServiceResponse("User deleted", HttpStatus.OK);
    }
}
