package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.*;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.factories.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceProxy {
    private final EventService eventService;
    private final ResponseFactory responseFactory;

    @Autowired
    public EventServiceProxy(EventService eventService, ResponseFactory responseFactory) {
        this.eventService = eventService;
        this.responseFactory = responseFactory;
    }

    public ServiceResponse<String> createEvent(CreateEventDto createEventDto) {
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
            return responseFactory.account().userNotFound(e.getMessage());
        }
    }

    public ServiceResponse<String> deleteEvent(DeleteEventDto deleteEventDto) {
        return eventService.deleteEvent(deleteEventDto.eventId(), deleteEventDto.sessionId());
    }

    public ServiceResponse<List<EventDataDto>> getOwnedEvents(RequestOwnedEventsDto requestOwnedEventsDto) {
        return eventService.getOwnedEvents(requestOwnedEventsDto.sessionId());
    }

    public ServiceResponse<List<EventDataDto>> getAuthorizedEvents(RequestAuthorizedEventsDto requestAuthorizedEventsDto) {
        return eventService.getAuthorizedEvents(requestAuthorizedEventsDto.sessionId());
    }

    public ServiceResponse<EventDataDto> getSpecificEvent(RequestSpecificEventDto requestSpecificEventDto) {
        return eventService.getSpecificEvent(requestSpecificEventDto.sessionId(), requestSpecificEventDto.eventId());
    }

    public ServiceResponse<String> updateSpecificEvent(UpdateEventDto updateEventDto) {
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

    public ServiceResponse<List<EventDataDto>> getLocationEvents(RequestLocationEventsDto requestLocationEventsDto) {
        return eventService.getLocationEvents(requestLocationEventsDto.sessionId(), requestLocationEventsDto.location());
    }
}
