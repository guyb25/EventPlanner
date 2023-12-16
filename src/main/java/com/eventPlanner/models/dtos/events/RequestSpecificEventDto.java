package com.eventPlanner.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestSpecificEventDto(@NotEmpty(message = "sessionId is required") String sessionId,
                                      @NotEmpty(message = "eventId is required") Long eventId) {
}
