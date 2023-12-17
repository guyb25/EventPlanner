package com.eventPlanner.core.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestLocationEventsDto(@NotEmpty String sessionId,
                                       @NotEmpty String location) {
}
