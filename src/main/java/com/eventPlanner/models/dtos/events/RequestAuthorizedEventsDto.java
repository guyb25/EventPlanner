package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.types.EventSortMethod;
import jakarta.validation.constraints.NotEmpty;

public record RequestAuthorizedEventsDto(@NotEmpty(message = "sessionId is required") String sessionId,
                                         @NotEmpty(message = "eventSortMethod is required") EventSortMethod eventSortMethod) {
}
