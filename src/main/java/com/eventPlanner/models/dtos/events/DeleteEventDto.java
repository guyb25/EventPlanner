package com.eventPlanner.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record DeleteEventDto(@NotEmpty Long eventId, @NotEmpty String sessionId) {
}
