package com.eventPlanner.models.dtos.account;

import com.eventPlanner.models.validation.constraints.SessionIdConstraint;

public record DeleteAccountDto(@SessionIdConstraint String sessionId) {
}
