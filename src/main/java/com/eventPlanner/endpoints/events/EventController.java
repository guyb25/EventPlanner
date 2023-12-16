package com.eventPlanner.endpoints.events;

import com.eventPlanner.models.dtos.events.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Management")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventServiceProxy) {
        this.eventService = eventServiceProxy;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@Valid CreateEventDto createEventDto) {
        return eventService.createEvent(createEventDto).toResponse();
    }

    @DeleteMapping("/delete/specific")
    public ResponseEntity<String> deleteEvent(@Valid DeleteEventDto deleteEventDto) {
        return eventService.deleteEvent(deleteEventDto).toResponse();
    }

    @PostMapping("/retrieve/owned")
    public ResponseEntity<List<EventDataDto>> retrieveOwnedEvents(@Valid RequestOwnedEventsDto requestOwnedEventsDto) {
        return eventService.getOwnedEvents(requestOwnedEventsDto).toResponse();
    }

    @PostMapping("/retrieve/authorized")
    public ResponseEntity<List<EventDataDto>> retrieveAuthorizedEvents(
            @Valid RequestAuthorizedEventsDto requestAuthorizedEventsDto) {
        return eventService.getAuthorizedEvents(requestAuthorizedEventsDto).toResponse();
    }

    @PostMapping("/retrieve/specific")
    public ResponseEntity<EventDataDto> retrieveSpecificEvent(@Valid RequestSpecificEventDto requestSpecificEventDto) {
        return eventService.getSpecificEvent(requestSpecificEventDto).toResponse();
    }

    @PutMapping("/update/specific")
    public ResponseEntity<String> updateSpecificEvent(@Valid UpdateEventDto updateEventDto) {
        return eventService.updateSpecificEvent(updateEventDto).toResponse();
    }

    @PostMapping("/retrieve/location")
    public ResponseEntity<List<EventDataDto>> retrieveLocationEvents(
            @Valid RequestLocationEventsDto requestLocationEventsDto) {
        return eventService.getLocationEvents(requestLocationEventsDto).toResponse();
    }
}
