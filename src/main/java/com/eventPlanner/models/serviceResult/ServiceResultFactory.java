package com.eventPlanner.models.serviceResult;

import org.springframework.http.HttpStatus;

public class ServiceResultFactory {
    public static ServiceResult usernameTaken() {
        return new ServiceResult("Username is already taken", HttpStatus.CONFLICT);
    }

    public static ServiceResult emailTaken() {
        return new ServiceResult("Email is already taken", HttpStatus.CONFLICT);
    }

    public static ServiceResult userCreated() {
        return new ServiceResult("User created successfully", HttpStatus.CREATED);
    }

    public static ServiceResult wrongUsernameOrPassword() {
        return new ServiceResult("Wrong username or password", HttpStatus.UNAUTHORIZED);
    }

    public static ServiceResult sessionCreated(String sessionId) {
        return new ServiceResult(sessionId, HttpStatus.CREATED);
    }

    public static ServiceResult invalidSession() {
        return new ServiceResult("Invalid session ID", HttpStatus.UNAUTHORIZED);
    }

    public static ServiceResult sessionEnded() {
        return new ServiceResult("Ended session", HttpStatus.OK);
    }
    public static ServiceResult userDeleted() {
        return new ServiceResult("User deleted", HttpStatus.OK);
    }

    public static ServiceResult userNotFound(String msg) {
        return new ServiceResult(msg, HttpStatus.NOT_FOUND);
    }

    public static ServiceResult eventCreatedSuccessfully(Long eventId) {
        return new ServiceResult("Event created successfully, event id: " + eventId, HttpStatus.CREATED);
    }

    public static ServiceResult eventDeleted() {
        return new ServiceResult("Event deleted.", HttpStatus.OK);
    }
}
