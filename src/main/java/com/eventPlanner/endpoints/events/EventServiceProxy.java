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

    public ServiceResult<String> createEvent(EventCreationDto eventCreationDto) {
        try {
            return eventService.createEvent(
                    eventCreationDto.name(),
                    eventCreationDto.sessionId(),
                    eventCreationDto.description(),
                    eventCreationDto.location(),
                    eventCreationDto.time(),
                    eventCreationDto.participants()
            );
        }

        catch (IllegalArgumentException e) {
            return ServiceResultFactory.userNotFound(e.getMessage());
        }
    }

    public ServiceResult<String> deleteEvent(EventDeletionDto eventDeletionDto) {
        return eventService.deleteEvent(eventDeletionDto.eventId(), eventDeletionDto.sessionId());
    }

    public ServiceResult<List<EventDataDto>> getOwnedEvents(OwnedEventsRequestDto ownedEventsRequestDto) {
        return eventService.getOwnedEvents(ownedEventsRequestDto.sessionId());
    }

    public ServiceResult<List<EventDataDto>> getAuthorizedEvents(AuthorizedEventsRequestDto authorizedEventsRequestDto) {
        return eventService.getOwnedEvents(authorizedEventsRequestDto.sessionId());
    }
}
