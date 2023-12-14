package com.eventPlanner.models.dtos.events;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreationDto(String name, String description, String location, LocalDateTime time, List<Long> participants) {
}
