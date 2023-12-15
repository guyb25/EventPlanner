package com.eventPlanner.dummyBuilders;

import com.eventPlanner.models.schemas.Event;

import java.time.LocalDateTime;

public class EventDummyBuilder {

    private Event event;

    public EventDummyBuilder generate() {
        event = new Event(
                RandomValueGenerator.randomUniqueLong(),
                RandomValueGenerator.randomUniqueString(),
                RandomValueGenerator.randomUniqueLong(),
                RandomValueGenerator.randomUniqueString(),
                RandomValueGenerator.randomUniqueString(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return this;
    }

    public EventDummyBuilder withHostId(Long hostId) {
        event.setHostId(hostId);
        return this;
    }

    public Event build() {
        return event;
    }
}
