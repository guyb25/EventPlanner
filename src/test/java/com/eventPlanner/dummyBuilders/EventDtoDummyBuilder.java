package com.eventPlanner.dummyBuilders;

import com.eventPlanner.models.dtos.events.CreateEventDto;
import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.schemas.Event;

import java.util.List;

public class EventDtoDummyBuilder {
    private EventDataDto eventDataDto;

    public EventDtoDummyBuilder fromEvent(Event event) {
        eventDataDto = new EventDataDto(
                event.getId(),
                event.getName(),
                RandomValueGenerator.randomUniqueString(),
                event.getDescription(),
                event.getLocation(),
                event.getTime(),
                event.getCreationTime(),
                List.of(RandomValueGenerator.randomUniqueString(), RandomValueGenerator.randomUniqueString())
        );

        return this;
    }

    public EventDtoDummyBuilder withHost(String host) {
        eventDataDto = new EventDataDto(
                eventDataDto.id(),
                eventDataDto.name(),
                host,
                eventDataDto.description(),
                eventDataDto.location(),
                eventDataDto.time(),
                eventDataDto.creationTime(),
                eventDataDto.participants()
        );

        return this;
    }

    public EventDtoDummyBuilder withParticipants(List<String> participants) {
        eventDataDto = new EventDataDto(
                eventDataDto.id(),
                eventDataDto.name(),
                eventDataDto.host(),
                eventDataDto.description(),
                eventDataDto.location(),
                eventDataDto.time(),
                eventDataDto.creationTime(),
                participants
        );
        return this;
    }

    public EventDataDto build() {
        return eventDataDto;
    }
}
