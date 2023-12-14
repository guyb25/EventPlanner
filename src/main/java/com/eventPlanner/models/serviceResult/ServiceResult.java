package com.eventPlanner.models.serviceResult;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceResult<T> {
    private T message;
    private final HttpStatus httpStatus;

    public ServiceResult(T message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ServiceResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ResponseEntity toResponse() {
        return message == null ?
                ResponseEntity.status(httpStatus).build() :
                ResponseEntity.status(httpStatus).body(message);
    }
}