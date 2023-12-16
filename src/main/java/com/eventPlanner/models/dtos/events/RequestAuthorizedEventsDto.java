package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.types.EventSortMethod;
import jakarta.validation.constraints.NotEmpty;

public record RequestAuthorizedEventsDto(@NotEmpty String sessionId, EventSortMethod eventSortMethod) {
}
