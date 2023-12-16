package com.eventPlanner.models.dtos.account;

import jakarta.validation.constraints.NotEmpty;

public record LoginAccountDto(@NotEmpty(message = "name is required") String name,
                              @NotEmpty(message = "password is required") String password) {
}
