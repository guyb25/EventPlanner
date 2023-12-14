package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.serviceResponse.serviceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GeneralResponseFactory {
    public serviceResponse success() {
        return new serviceResponse(HttpStatus.OK);
    }

    public serviceResponse unauthorized() {
        return new serviceResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
