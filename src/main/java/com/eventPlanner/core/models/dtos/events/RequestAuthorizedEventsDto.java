package com.eventPlanner.core.models.dtos.events;

import jakarta.validation.constraints.NotEmpty;

public record RequestAuthorizedEventsDto(@NotEmpty String sessionId,
                                         @NotEmpty EventSortMethod eventSortMethod) {
}
