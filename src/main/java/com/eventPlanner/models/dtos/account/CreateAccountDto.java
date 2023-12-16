package com.eventPlanner.models.dtos.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateAccountDto(
        @NotEmpty(message = "username is required") String name,
        @NotEmpty(message = "password is required") String password,
        @NotEmpty(message = "email is required")
        @Schema(example = "example@gmail.com")
        String email) {
}