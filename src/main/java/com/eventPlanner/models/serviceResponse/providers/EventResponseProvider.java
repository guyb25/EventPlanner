package com.eventPlanner.models.serviceResponse.providers;

import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResponseProvider {
    public ServiceResponse eventCreated(Long eventId) {
        return new ServiceResponse("Event created successfully, event id: " + eventId, HttpStatus.CREATED);
    }

    public ServiceResponse eventDeleted() {
        return new ServiceResponse("Event deleted.", HttpStatus.OK);
    }

    public ServiceResponse eventNotFound(Long eventId) {
        return new ServiceResponse("Event not found: " + eventId, HttpStatus.NOT_FOUND);
    }

    public ServiceResponse eventDataList(List<EventDataDto> eventDataDtoList) {
        return new ServiceResponse(eventDataDtoList, HttpStatus.OK);
    }

    public ServiceResponse eventData(EventDataDto eventDataDto) {
        return new ServiceResponse(eventDataDto, HttpStatus.OK);
    }
}
