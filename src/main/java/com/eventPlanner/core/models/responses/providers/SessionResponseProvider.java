package com.eventPlanner.core.models.responses.providers;

import com.eventPlanner.core.models.responses.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SessionResponseProvider {
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
