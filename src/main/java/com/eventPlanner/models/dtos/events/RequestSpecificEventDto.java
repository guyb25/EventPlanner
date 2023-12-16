package com.eventPlanner.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestSpecificEventDto(@NotEmpty String sessionId, @NotEmpty Long eventId) {
}
