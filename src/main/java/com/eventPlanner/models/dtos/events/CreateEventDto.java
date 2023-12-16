package com.eventPlanner.models.dtos.events;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventDto(
        @NotEmpty String name,
        @NotEmpty String sessionId,
        @NotEmpty String description,
        @NotEmpty String location,
        @NotEmpty @Schema(type = "string", example = "2023-12-14T09:03:32") LocalDateTime time,
        @NotEmpty List<String> participants) {
}
