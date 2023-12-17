package com.eventPlanner.core.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestSpecificEventDto(@NotEmpty String sessionId,
                                      @NotEmpty() Long eventId) {
}
