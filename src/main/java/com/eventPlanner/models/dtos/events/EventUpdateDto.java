package com.eventPlanner.models.dtos.events;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record EventUpdateDto(
        String sessionId,
        Long eventId,
        String name,
        String description,
        String location,
        @Schema(type = "string", example = "2023-12-14T09:03:32")LocalDateTime time,
        List<String> participants
){}