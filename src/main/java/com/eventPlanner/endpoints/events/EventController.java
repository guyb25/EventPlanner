package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createEvent(CreateEventDto createEventDto) {
        return eventServiceProxy.createEvent(createEventDto).toResponse();
    }

    @DeleteMapping("/delete/specific")
    public ResponseEntity<String> deleteEvent(DeleteEventDto deleteEventDto) {
        return eventServiceProxy.deleteEvent(deleteEventDto).toResponse();
    }

    @PostMapping("/retrieve/owned")
    public ResponseEntity<List<EventDataDto>> retrieveOwnedEvents(RequestOwnedEventsDto requestOwnedEventsDto) {
        return eventServiceProxy.getOwnedEvents(requestOwnedEventsDto).toResponse();
    }

    @PostMapping("/retrieve/authorized")
    public ResponseEntity<List<EventDataDto>> retrieveAuthorizedEvents(RequestAuthorizedEventsDto requestAuthorizedEventsDto) {
        return eventServiceProxy.getAuthorizedEvents(requestAuthorizedEventsDto).toResponse();
    }

    @PostMapping("/retrieve/specific")
    public ResponseEntity<EventDataDto> retrieveSpecificEvent(RequestSpecificEventDto requestSpecificEventDto) {
        return eventServiceProxy.getSpecificEvent(requestSpecificEventDto).toResponse();
    }

    @PutMapping("/update/specific")
    public ResponseEntity<String> updateSpecificEvent(UpdateEventDto updateEventDto) {
        return eventServiceProxy.updateSpecificEvent(updateEventDto).toResponse();
    }
}
