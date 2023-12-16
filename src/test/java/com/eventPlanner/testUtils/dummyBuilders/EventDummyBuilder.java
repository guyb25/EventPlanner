package com.eventPlanner.testUtils.dummyBuilders;

import com.eventPlanner.models.schemas.Event;
import com.eventPlanner.testUtils.UniqueValueGenerator;

import java.time.LocalDateTime;

public class EventDummyBuilder {

    private Event event;

    public EventDummyBuilder generate() {
        event = new Event(
                UniqueValueGenerator.uniqueLong(),
                UniqueValueGenerator.uniqueString(),
                UniqueValueGenerator.uniqueLong(),
                UniqueValueGenerator.uniqueString(),
                UniqueValueGenerator.uniqueString(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return this;
    }

    public EventDummyBuilder withEventId(Long eventId) {
        event.setId(eventId);
        return this;
    }

    public EventDummyBuilder withHostId(Long hostId) {
        event.setHostId(hostId);
        return this;
    }

    public EventDummyBuilder withLocation(String location) {
        event.setLocation(location);
        return this;
    }

    public Event build() {
        return event;
    }
}
