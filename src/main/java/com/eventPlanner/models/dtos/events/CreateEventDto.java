package com.eventPlanner.models.dtos.events;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventDto(
        @NotEmpty(message = "name is required") String name,
        @NotEmpty(message = "sessionId is required") String sessionId,
        @NotEmpty(message = "description is required") String description,
        @NotEmpty(message = "location is required") String location,
        @NotEmpty(message = "time is required") @Schema(type = "string", example = "2023-12-14T09:03:32") LocalDateTime time,
        @NotEmpty List<String> participants) {
}
