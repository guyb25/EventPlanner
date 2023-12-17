package com.eventPlanner.core.models.dtos.account;

import com.eventPlanner.core.models.constraints.account.UserConstraint;
import com.eventPlanner.core.models.constraints.account.PasswordConstraint;
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