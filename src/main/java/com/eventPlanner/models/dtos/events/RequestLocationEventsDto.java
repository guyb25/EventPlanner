package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.validation.constraints.SessionIdConstraint;
import com.eventPlanner.models.validation.messages.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;

public record RequestLocationEventsDto(@SessionIdConstraint String sessionId,
                                       @NotEmpty(message = ValidationMessages.required) String location) {
}
