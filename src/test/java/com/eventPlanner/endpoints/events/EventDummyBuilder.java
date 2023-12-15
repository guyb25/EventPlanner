package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.schemas.Event;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.UUID;

public class EventDummyBuilder {
    private static final AtomicLong uniqueId = new AtomicLong(ThreadLocalRandom.current().nextLong());
    private Event event;

    public EventDummyBuilder generate() {
        event = new Event(
                randomUniqueLong(),
                randomUniqueString(),
                randomUniqueLong(),
                randomUniqueString(),
                randomUniqueString(),
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

    private String randomUniqueString() {
        return UUID.randomUUID().toString();
    }

    private Long randomUniqueLong() {
        return uniqueId.getAndIncrement();
    }
}
