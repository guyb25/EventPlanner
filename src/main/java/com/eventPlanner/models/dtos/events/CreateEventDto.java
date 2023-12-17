package com.eventPlanner.models.dtos.events;

import com.eventPlanner.models.validation.constraints.SessionIdConstraint;
import com.eventPlanner.models.validation.constraints.event.EventNameConstraint;
import com.eventPlanner.models.validation.messages.EventValidationMessages;
import com.eventPlanner.models.validation.messages.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventDto(
        @EventNameConstraint
        String name,
        @SessionIdConstraint String sessionId,
        @NotEmpty(message = ValidationMessages.required) String description,
        @NotEmpty(message = ValidationMessages.required) String location,
        @NotEmpty(message = ValidationMessages.required)
        @Schema(type = "string", example = "2023-12-14T09:03:32")
        LocalDateTime time,
        @NotEmpty List<String> participants) {
}
