package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.*;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceProxy {
    private final EventService eventService;
    private final ResponseProvider responseProvider;

    @Autowired
    public EventServiceProxy(EventService eventService, ResponseProvider responseProvider) {
        this.eventService = eventService;
        this.responseProvider = responseProvider;
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
            return responseProvider.account().userNotFound(e.getMessage());
        }
    }

    public ServiceResponse<String> deleteEvent(DeleteEventDto deleteEventDto) {
        return eventService.deleteEvent(deleteEventDto.eventId(), deleteEventDto.sessionId());
    }

    public ServiceResponse<List<EventDataDto>> getOwnedEvents(RequestOwnedEventsDto requestOwnedEventsDto) {
        return eventService.getOwnedEvents(requestOwnedEventsDto.sessionId());
    }

    public ServiceResponse<List<EventDataDto>> getAuthorizedEvents(RequestAuthorizedEventsDto requestAuthorizedEventsDto) {
        return eventService.getAuthorizedEvents(requestAuthorizedEventsDto.sessionId(), requestAuthorizedEventsDto.eventSortMethod());
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
