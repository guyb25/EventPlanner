package com.eventPlanner.models.dtos.account;

import jakarta.validation.constraints.NotEmpty;

public record DeleteAccountDto(@NotEmpty(message = "sessionId is required") String sessionId) {
}
