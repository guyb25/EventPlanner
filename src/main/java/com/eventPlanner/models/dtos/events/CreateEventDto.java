package com.eventPlanner.models.dtos.events;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventDto(
        String name,
        String sessionId,
        String description,
        String location,
        @Schema(type = "string", example = "2023-12-14T09:03:32") LocalDateTime time,
        List<String> participants) {
}
