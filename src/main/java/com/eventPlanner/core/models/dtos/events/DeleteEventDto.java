package com.eventPlanner.core.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DeleteEventDto(@NotNull Long eventId,
                             @NotEmpty String sessionId) {
}
