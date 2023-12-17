package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.validation.constraints.SessionIdConstraint;
import jakarta.validation.constraints.NotEmpty;

public record RequestOwnedEventsDto(@SessionIdConstraint String sessionId) {
}
