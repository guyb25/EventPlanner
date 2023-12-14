package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.EventCreationDto;
import com.eventPlanner.models.serviceResult.ServiceResult;
import com.eventPlanner.models.serviceResult.ServiceResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceProxy {
    private final EventService eventService;

    @Autowired
    public EventServiceProxy(EventService eventService) {
        this.eventService = eventService;
    }

    public ServiceResult<String> CreateEvent(EventCreationDto eventCreationDto) {
        try {
            return eventService.CreateEvent(
                    eventCreationDto.name(),
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
}
