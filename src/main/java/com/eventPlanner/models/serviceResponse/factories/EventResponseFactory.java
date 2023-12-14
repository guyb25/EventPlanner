package com.eventPlanner.models.serviceResponse.factories;

import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.serviceResponse.serviceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResponseFactory {
    public serviceResponse eventCreatedSuccessfully(Long eventId) {
        return new serviceResponse("Event created successfully, event id: " + eventId, HttpStatus.CREATED);
    }

    public serviceResponse eventDeleted() {
        return new serviceResponse("Event deleted.", HttpStatus.OK);
    }

    public serviceResponse eventNotFound(Long eventId) {
        return new serviceResponse("Event not found: " + eventId, HttpStatus.NOT_FOUND);
    }

    public serviceResponse eventDataList(List<EventDataDto> eventDataDtoList) {
        return new serviceResponse(eventDataDtoList, HttpStatus.OK);
    }

    public serviceResponse eventData(EventDataDto eventDataDto) {
        return new serviceResponse(eventDataDto, HttpStatus.OK);
    }
}
