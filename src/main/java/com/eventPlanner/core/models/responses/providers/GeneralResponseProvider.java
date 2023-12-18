package com.eventPlanner.core.models.responses.providers;

import com.eventPlanner.core.models.responses.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GeneralResponseProvider {
    public ServiceResponse success() {
        return new ServiceResponse(HttpStatus.OK);
    }

    public ServiceResponse unauthorized() {
        return new ServiceResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
