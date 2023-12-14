package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Management")
public class EventController {
    private final EventServiceProxy eventServiceProxy;

    @Autowired
    public EventController(EventServiceProxy eventServiceProxy) {
        this.eventServiceProxy = eventServiceProxy;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(EventCreationDto eventCreationDto) {
        return eventServiceProxy.createEvent(eventCreationDto).toResponse();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(EventDeletionDto eventDeletionDto) {
        return eventServiceProxy.deleteEvent(eventDeletionDto).toResponse();
    }

    @PostMapping("/retrieve/owned")
    public ResponseEntity<List<EventDataDto>> retrieveOwnedEvents(OwnedEventsRequestDto ownedEventsRequestDto) {
        return eventServiceProxy.getOwnedEvents(ownedEventsRequestDto).toResponse();
    }

    @PostMapping("/retrieve/authorized")
    public ResponseEntity<List<EventDataDto>> retrieveAuthorizedEvents(AuthorizedEventsRequestDto authorizedEventsRequestDto) {
        return eventServiceProxy.getAuthorizedEvents(authorizedEventsRequestDto).toResponse();
    }

    @PostMapping("/retrieve/specific")
    public ResponseEntity<EventDataDto> retrieveSpecificEvent(SpecificEventRequestDto specificEventRequestDto) {
        return eventServiceProxy.getSpecificEvent(specificEventRequestDto).toResponse();
    }
}
