package com.eventPlanner.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestOwnedEventsDto(@NotEmpty(message = "sessionId is required") String sessionId) {
}
