package com.eventPlanner.core.models.dtos.account;

import jakarta.validation.constraints.NotEmpty;

public record DeleteAccountDto(@NotEmpty String sessionId) {
}
