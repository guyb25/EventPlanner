package com.eventPlanner.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record DeleteEventDto(@NotEmpty(message = "eventId is required") Long eventId,
                             @NotEmpty(message = "sessionId is required") String sessionId) {
}
