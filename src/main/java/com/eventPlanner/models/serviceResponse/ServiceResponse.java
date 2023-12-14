package com.eventPlanner.models.serviceResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceResponse<T> {
    private T message;
    private final HttpStatus httpStatus;

    public ServiceResponse(T message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ServiceResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ResponseEntity toResponse() {
        return message == null ?
                ResponseEntity.status(httpStatus).build() :
                ResponseEntity.status(httpStatus).body(message);
    }
}