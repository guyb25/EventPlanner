package com.eventPlanner.core.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestOwnedEventsDto(@NotEmpty String sessionId) {
}
