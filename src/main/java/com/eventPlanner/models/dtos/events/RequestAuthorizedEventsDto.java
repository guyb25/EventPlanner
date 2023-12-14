package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.types.EventSortMethod;

public record RequestAuthorizedEventsDto(String sessionId, EventSortMethod eventSortMethod) {
}
