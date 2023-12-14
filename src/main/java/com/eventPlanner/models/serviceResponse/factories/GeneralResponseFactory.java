package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.serviceResponse.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GeneralResponseFactory {
    public ServiceResponse success() {
        return new ServiceResponse(HttpStatus.OK);
    }

    public ServiceResponse unauthorized() {
        return new ServiceResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
