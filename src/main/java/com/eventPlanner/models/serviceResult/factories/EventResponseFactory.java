package com.eventPlanner.models.serviceResult.factories;

import com.eventPlanner.models.dtos.events.EventDataDto;
import com.eventPlanner.models.serviceResult.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResponseFactory {
    public ServiceResult eventCreatedSuccessfully(Long eventId) {
        return new ServiceResult("Event created successfully, event id: " + eventId, HttpStatus.CREATED);
    }

    public ServiceResult eventDeleted() {
        return new ServiceResult("Event deleted.", HttpStatus.OK);
    }

    public ServiceResult eventNotFound(Long eventId) {
        return new ServiceResult("Event not found: " + eventId, HttpStatus.NOT_FOUND);
    }

    public ServiceResult eventDataList(List<EventDataDto> eventDataDtoList) {
        return new ServiceResult(eventDataDtoList, HttpStatus.OK);
    }

    public ServiceResult eventData(EventDataDto eventDataDto) {
        return new ServiceResult(eventDataDto, HttpStatus.OK);
    }
}
