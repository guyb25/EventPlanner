package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.*;
import com.eventPlanner.models.serviceResult.ServiceResult;
import com.eventPlanner.models.serviceResult.ServiceResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceProxy {
    private final EventService eventService;

    @Autowired
    public EventServiceProxy(EventService eventService) {
        this.eventService = eventService;
    }

    public ServiceResult<String> createEvent(CreateEventDto createEventDto) {
        try {
            return eventService.createEvent(
                    createEventDto.name(),
                    createEventDto.sessionId(),
                    createEventDto.description(),
                    createEventDto.location(),
                    createEventDto.time(),
                    createEventDto.participants()
            );
        }

        catch (IllegalArgumentException e) {
            return ServiceResultFactory.userNotFound(e.getMessage());
        }
    }

    public ServiceResult<String> deleteEvent(DeleteEventDto deleteEventDto) {
        return eventService.deleteEvent(deleteEventDto.eventId(), deleteEventDto.sessionId());
    }

    public ServiceResult<List<EventDataDto>> getOwnedEvents(RequestOwnedEventsDto requestOwnedEventsDto) {
        return eventService.getOwnedEvents(requestOwnedEventsDto.sessionId());
    }

    public ServiceResult<List<EventDataDto>> getAuthorizedEvents(RequestAuthorizedEventsDto requestAuthorizedEventsDto) {
        return eventService.getAuthorizedEvents(requestAuthorizedEventsDto.sessionId());
    }

    public ServiceResult<EventDataDto> getSpecificEvent(RequestSpecificEventDto requestSpecificEventDto) {
        return eventService.getSpecificEvent(requestSpecificEventDto.sessionId(), requestSpecificEventDto.eventId());
    }

    public ServiceResult<String> updateSpecificEvent(UpdateEventDto updateEventDto) {
        return eventService.updateSpecificEvent(
                updateEventDto.sessionId(),
                updateEventDto.eventId(),
                updateEventDto.name(),
                updateEventDto.description(),
                updateEventDto.location(),
                updateEventDto.time(),
                updateEventDto.participants()
        );
    }
}
