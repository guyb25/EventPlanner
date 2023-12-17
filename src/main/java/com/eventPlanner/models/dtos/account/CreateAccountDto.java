package com.eventPlanner.models.dtos.account;

import com.eventPlanner.models.validation.constraints.account.PasswordConstraint;
import com.eventPlanner.models.validation.constraints.account.UserConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record CreateAccountDto(
        @UserConstraint
        String name,
        @PasswordConstraint
        String password,
        @Email
        @Schema(example = "example@gmail.com")
        String email) {
}