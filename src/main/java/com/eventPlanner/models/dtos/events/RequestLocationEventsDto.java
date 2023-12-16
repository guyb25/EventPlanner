package com.eventPlanner.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestLocationEventsDto(@NotEmpty(message = "sessionId is required") String sessionId,
                                       @NotEmpty(message = "location is required") String location) {
}
