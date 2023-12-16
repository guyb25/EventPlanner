package com.eventPlanner.models.dtos.account;

import jakarta.validation.constraints.NotEmpty;

public record LogoutAccountDto(@NotEmpty(message = "sessionId is required") String sessionId) {
}
