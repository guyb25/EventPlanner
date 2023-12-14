package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.serviceResponse.serviceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountResponseFactory {
    public serviceResponse usernameTaken() {
        return new serviceResponse("Username is already taken", HttpStatus.CONFLICT);
    }

    public serviceResponse emailTaken() {
        return new serviceResponse("Email is already taken", HttpStatus.CONFLICT);
    }

    public serviceResponse userCreated() {
        return new serviceResponse("User created successfully", HttpStatus.CREATED);
    }

    public serviceResponse wrongUsernameOrPassword() {
        return new serviceResponse("Wrong username or password", HttpStatus.UNAUTHORIZED);
    }

    public serviceResponse userNotFound(String msg) {
        return new serviceResponse(msg, HttpStatus.NOT_FOUND);
    }

    public serviceResponse userDeleted() {
        return new serviceResponse("User deleted", HttpStatus.OK);
    }
}
