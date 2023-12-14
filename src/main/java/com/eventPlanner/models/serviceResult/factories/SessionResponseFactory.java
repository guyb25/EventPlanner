package com.eventPlanner.models.serviceResult.factories;

import com.eventPlanner.models.serviceResult.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionResponseFactory {
    public ServiceResult sessionCreated(String sessionId) {
        return new ServiceResult(sessionId, HttpStatus.CREATED);
    }

    public ServiceResult invalidSession() {
        return new ServiceResult("Invalid session ID", HttpStatus.UNAUTHORIZED);
    }

    public ServiceResult sessionEnded() {
        return new ServiceResult("Ended session", HttpStatus.OK);
    }
}
