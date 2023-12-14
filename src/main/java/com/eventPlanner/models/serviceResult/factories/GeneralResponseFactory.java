package com.eventPlanner.models.serviceResult.factories;

import com.eventPlanner.models.serviceResult.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GeneralResponseFactory {
    public ServiceResult success() {
        return new ServiceResult(HttpStatus.OK);
    }

    public ServiceResult unauthorized() {
        return new ServiceResult("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
