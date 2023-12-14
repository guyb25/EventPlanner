package com.eventPlanner.models.serviceResult;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ServiceResult<T>(T message, HttpStatus httpStatus) {

    public ResponseEntity toResponse() {
        return ResponseEntity.status(this.httpStatus).body(this.message);
    }
}
