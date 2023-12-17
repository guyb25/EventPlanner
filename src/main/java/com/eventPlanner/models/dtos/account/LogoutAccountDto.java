package com.eventPlanner.models.dtos.account;

import com.eventPlanner.models.validation.constraints.SessionIdConstraint;

public record LogoutAccountDto(@SessionIdConstraint String sessionId) {
}
