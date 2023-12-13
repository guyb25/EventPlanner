package com.eventPlanner.models.serviceResult;

import org.springframework.http.HttpStatus;

public class ServiceResult<T> {
    private final T message;
    private final HttpStatus httpStatus;


    public ServiceResult(T message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public T getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
