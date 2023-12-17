package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.validation.constraints.SessionIdConstraint;
import com.eventPlanner.models.validation.messages.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;

public record DeleteEventDto(@NotEmpty(message = ValidationMessages.required) Long eventId,
                             @SessionIdConstraint String sessionId) {
}
