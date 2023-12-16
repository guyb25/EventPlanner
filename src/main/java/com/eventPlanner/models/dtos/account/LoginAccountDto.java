package com.eventPlanner.models.dtos.account;

import jakarta.validation.constraints.NotEmpty;

public record LoginAccountDto(@NotEmpty String name, @NotEmpty String password) {
}
