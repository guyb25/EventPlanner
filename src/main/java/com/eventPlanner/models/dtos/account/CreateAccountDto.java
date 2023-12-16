package com.eventPlanner.models.dtos.account;

import jakarta.validation.constraints.NotEmpty;

public record CreateAccountDto(@NotEmpty String name, @NotEmpty String password, @NotEmpty String email) {
}