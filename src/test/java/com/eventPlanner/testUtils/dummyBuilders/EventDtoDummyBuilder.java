package com.eventPlanner.testUtils.dummyBuilders;

import com.eventPlanner.core.models.dtos.events.EventDataDto;
import com.eventPlanner.dataAccess.userEvents.schemas.Event;
import com.eventPlanner.testUtils.UniqueValueGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class EventDtoDummyBuilder {
    private Long eventId;
    private String eventName;
    private String host;
    private String description;
    private String location;
    private LocalDateTime time;
    private LocalDateTime creationTime;
    private List<String> participants;


    public EventDtoDummyBuilder fromEvent(Event event) {
        eventId = event.getId();
        eventName = event.getName();
        host = UniqueValueGenerator.uniqueString();
        description = event.getDescription();
        location = event.getLocation();
        time = event.getTime();
        creationTime = event.getCreationTime();
        participants = List.of(UniqueValueGenerator.uniqueString(), UniqueValueGenerator.uniqueString());
        return this;
    }

    public EventDtoDummyBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public EventDtoDummyBuilder withParticipants(List<String> participants) {
        this.participants = participants;
        return this;
    }

    public EventDataDto build() {
        return new EventDataDto(
                eventId,
                eventName,
                host,
                description,
                location,
                time,
                creationTime,
                participants
        );
    }
}
