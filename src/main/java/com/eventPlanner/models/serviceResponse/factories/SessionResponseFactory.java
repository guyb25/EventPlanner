package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.serviceResponse.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionResponseFactory {
    public ServiceResponse sessionCreated(String sessionId) {
        return new ServiceResponse(sessionId, HttpStatus.CREATED);
    }

    public ServiceResponse invalidSession() {
        return new ServiceResponse("Invalid session ID", HttpStatus.UNAUTHORIZED);
    }

    public ServiceResponse sessionEnded() {
        return new ServiceResponse("Ended session", HttpStatus.OK);
    }
}
