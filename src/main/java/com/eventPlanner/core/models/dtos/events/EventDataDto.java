package com.eventPlanner.core.models.dtos.events;

import java.time.LocalDateTime;
import java.util.List;

public record EventDataDto(Long id, String name, String host, String description, String location, LocalDateTime time,
                           LocalDateTime creationTime, List<String> participants) {
}
