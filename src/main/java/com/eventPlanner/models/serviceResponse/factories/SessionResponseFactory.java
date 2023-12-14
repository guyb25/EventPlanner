package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.serviceResponse.serviceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionResponseFactory {
    public serviceResponse sessionCreated(String sessionId) {
        return new serviceResponse(sessionId, HttpStatus.CREATED);
    }

    public serviceResponse invalidSession() {
        return new serviceResponse("Invalid session ID", HttpStatus.UNAUTHORIZED);
    }

    public serviceResponse sessionEnded() {
        return new serviceResponse("Ended session", HttpStatus.OK);
    }
}
