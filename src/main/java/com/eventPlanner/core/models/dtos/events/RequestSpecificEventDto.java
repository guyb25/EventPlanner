package com.eventPlanner.core.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestSpecificEventDto(@NotEmpty String sessionId,
                                      @NotNull Long eventId) {
}
